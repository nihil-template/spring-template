package dev.nihilncunia.fa_campaign_manager.common.helper;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class SortHelper {
  private SortHelper() {}

  /**
   * 엔티티의 Q타입 객체를 기반으로 동적 정렬을 생성합니다.
   * 엔티티에 실제로 존재하는 필드만 정렬 대상으로 허용합니다 (보안 화이트리스트).
   *
   * @param sortList     정렬 토큰 목록(예: ["id,desc", "name,asc"])
   * @param qEntity      대상 엔티티의 Q타입 객체
   * @param defaultOrder 기본 정렬 조건
   * @param <E>          엔티티 타입
   * @return OrderSpecifier 배열
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <E> OrderSpecifier<?>[] toDynamicOrderSpecifiers(
    List<String> sortList,
    EntityPathBase<E> qEntity,
    OrderSpecifier<?> defaultOrder
  ) {
    if (sortList == null || sortList.isEmpty()) {
      return new OrderSpecifier<?>[]{ defaultOrder };
    }

    PathBuilder<E> path = new PathBuilder<>(qEntity.getType(), qEntity.getMetadata());
    List<OrderSpecifier<?>> result = new ArrayList<>();

    // 엔티티의 실제 필드(이름:타입) 맵 추출 (화이트리스트)
    Map<String, Class<?>> entityFields = getEntityFields(qEntity.getType());

    for (String token : sortList) {
      if (token == null || token.isBlank()) continue;

      String[] parts = token.split(",", 2);
      String column = parts[0].trim();
      String direction = (parts.length == 2) ? parts[1].trim().toLowerCase() : "asc";

      // 엔티티에 없는 필드면 무시
      if (!entityFields.containsKey(column)) continue;

      Order order = "desc".equals(direction) ? Order.DESC : Order.ASC;
      
      try {
        // 필드 타입을 함께 전달하여 ComparableExpressionBase 생성
        Class<?> fieldType = entityFields.get(column);
        // Comparable을 상속받은 타입인지 확인 후 처리 (안전한 캐스팅을 위해 raw 타입 사용)
        ComparableExpressionBase<?> expr = path.getComparable(column, (Class) fieldType);
        result.add(new OrderSpecifier(order, expr));
      } catch (Exception ignored) {
        // 경로 생성 실패 시 스킵
      }
    }

    return result.isEmpty() ? new OrderSpecifier<?>[]{ defaultOrder } : result.toArray(new OrderSpecifier<?>[0]);
  }

  /**
   * 클래스와 부모 클래스들의 모든 필드명과 타입을 추출합니다.
   */
  private static Map<String, Class<?>> getEntityFields(Class<?> clazz) {
    Map<String, Class<?>> fields = new java.util.HashMap<>();
    Class<?> current = clazz;
    while (current != null && current != Object.class) {
      for (Field field : current.getDeclaredFields()) {
        fields.put(field.getName(), field.getType());
      }
      current = current.getSuperclass();
    }
    return fields;
  }

  /**
   * 문자열 기반 정렬 파라미터 목록을 {@link OrderSpecifier} 배열로 변환합니다.
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static OrderSpecifier<?>[] toOrderSpecifiers(
    List<String> sortList,
    Map<String, ComparableExpressionBase<?>> sortMap,
    OrderSpecifier<?> defaultOrder
  ) {

    // 정렬 파라미터가 없으면 기본 정렬 1개만 반환합니다.
    if (sortList == null || sortList.isEmpty()) {
      return new OrderSpecifier<?>[]{ defaultOrder };
    }

    // 유효한 토큰만 선별해 누적합니다.
    List<OrderSpecifier<?>> result = new ArrayList<>();

    for (String token : sortList) {
      // null/blank 토큰은 무시
      if (token == null || token.isBlank()) continue;

      // "column,direction" 형태를 최대 2조각으로 분리 (direction에 ','가 더 있어도 무시)
      String[] parts = token.split(",", 2);
      String column = parts[0].trim();
      String direction = (parts.length == 2) ? parts[1].trim().toLowerCase() : "asc";

      // 화이트리스트에 없는 컬럼이면 무시(임의 컬럼 정렬 방지)
      ComparableExpressionBase<?> expr = sortMap.get(column);
      if (expr == null) continue;

      // direction이 desc일 때만 DESC, 나머지는 ASC로 처리(오타/기타 값은 asc로 폴백)
      Order order = "desc".equals(direction) ? Order.DESC : Order.ASC;

      // 제네릭 캡처 문제를 피하기 위해 raw 타입 생성(경고는 suppress 처리)
      // expr이 ComparableExpressionBase<?> 이므로 런타임에는 안전하게 OrderSpecifier가 생성됩니다.
      result.add(new OrderSpecifier(order, expr));
    }

    // 유효한 정렬이 하나도 없으면 기본 정렬 적용
    if (result.isEmpty()) {
      return new OrderSpecifier<?>[]{ defaultOrder };
    }

    // Querydsl orderBy(varargs)에 바로 넣을 수 있도록 배열로 변환
    return result.toArray(new OrderSpecifier<?>[0]);
  }
}
