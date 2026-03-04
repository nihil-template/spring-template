package dev.nihilncunia.fa_campaign_manager.common.helper;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import dev.nihilncunia.fa_campaign_manager.common.annotation.SearchCondition;

import java.lang.reflect.Field;

public final class SearchHelper {
  private SearchHelper() {}
  
  /**
   * DTO의 어노테이션 필드와 동적 검색 필드(searchType/searchKeyword)를 분석하여 조건을 생성합니다.
   *
   * @param dto     검색 조건을 담고 있는 DTO 객체
   * @param qEntity 대상 엔티티의 Q타입 객체
   * @param <E>     엔티티 타입
   * @return 조립된 BooleanBuilder
   */
  public static <E> BooleanBuilder buildAll(Object dto, EntityPathBase<E> qEntity) {
    BooleanBuilder builder = new BooleanBuilder();
    PathBuilder<E> path = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata());
    Class<?> dtoClass = dto.getClass();
    
    // 1. @SearchCondition 기반 개별 필터링 처리
    processAnnotations(dto, dtoClass, path, builder);
    
    // 2. 타입 지정형 동적 검색 처리 (searchType + searchKeyword)
    processDynamicSearch(dto, dtoClass, path, builder);
    
    return builder;
  }
  
  /**
   * &#064;SearchCondition 어노테이션이 붙은 필드를 순회하며 AND 조건을 생성합니다.
   * @param dto 검색 조건을 담고 있는 DTO 객체
   * @param dtoClass DTO 클래스 타입
   * @param path Querydsl 경로 빌더
   * @param builder BooleanBuilder 객체
   * @param <E> 엔티티 타입
   */
  private static <E> void processAnnotations(Object dto, Class<?> dtoClass, PathBuilder<E> path, BooleanBuilder builder) {
    Field[] fields = dtoClass.getDeclaredFields();
    for (Field field : fields) {
      SearchCondition annotation = field.getAnnotation(SearchCondition.class);
      if (annotation == null) continue;
      
      field.setAccessible(true);
      try {
        Object value = field.get(dto);
        if (isEmpty(value)) continue;
        
        String targetField = annotation.fieldName().isEmpty() ? field.getName() : annotation.fieldName();
        
        switch (annotation.type()) {
          case EQUALS -> {
            @SuppressWarnings("unchecked")
            Class<Object> valueType = (Class<Object>) value.getClass();
            SimpleExpression<Object> expr = path.get(targetField, valueType);
            builder.and(expr.eq(value));
          }
          case LIKE -> {
            if (value instanceof String str) {
              builder.and(path.getString(targetField).containsIgnoreCase(str.trim()));
            }
          }
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException("필드 접근에 실패하였습니다: " + field.getName(), e);
      }
    }
  }
  
  /**
   * searchType과 searchKeyword가 모두 존재할 때만 해당 필드에 대한 LIKE 조건을 생성합니다.
   * @param dto 검색 조건을 담고 있는 DTO 객체
   * @param dtoClass DTO 클래스 타입
   * @param path Querydsl 경로 빌더
   * @param builder BooleanBuilder 객체
   * @param <E> 엔티티 타입
   */
  private static <E> void processDynamicSearch(Object dto, Class<?> dtoClass, PathBuilder<E> path, BooleanBuilder builder) {
    try {
      Object typeObj = getFieldValue(dto, dtoClass, "searchType");
      Object keywordObj = getFieldValue(dto, dtoClass, "searchKeyword");
      
      // 타입과 키워드가 모두 유효할 때만 쿼리 생성 (마스터의 요청 반영)
      if (!isEmpty(typeObj) && !isEmpty(keywordObj)) {
        String type = ((String) typeObj).trim();
        String keyword = ((String) keywordObj).trim();
        
        // searchType이 필드명과 일치한다고 가정하고 LIKE 조건 생성
        if (!"all".equalsIgnoreCase(type)) {
          builder.and(path.getString(type).containsIgnoreCase(keyword));
        }
      }
    } catch (NoSuchFieldException ignored) {
      // DTO에 해당 검색 필드들이 없는 경우 스킵
    } catch (Exception e) {
      throw new RuntimeException("동적 검색 조건 생성 중 오류가 발생하였습니다.", e);
    }
  }
  
  /**
   * 리플렉션을 사용하여 객체의 필드 값을 가져옵니다.
   * @param dto 대상 객체
   * @param clazz 대상 클래스 타입
   * @param fieldName 필드 이름
   * @return 필드 값
   * @throws Exception 예외
   */
  private static Object getFieldValue(Object dto, Class<?> clazz, String fieldName) throws Exception {
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    return field.get(dto);
  }
  
  /**
   * 객체가 비어있는지 확인합니다.
   * @param value 확인할 객체
   * @return 비어있으면 true, 아니면 false
   */
  private static boolean isEmpty(Object value) {
    if (value == null) return true;
    if (value instanceof String s) return s.isBlank();
    return false;
  }
}
