package dev.nihilncunia.base.common.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.nihilncunia.base.common.dto.SearchDto;
import dev.nihilncunia.base.common.helper.SortHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class CustomRepositorySupport {
  
  protected final JPAQueryFactory jpaQueryFactory;
  
  /**
   * 페이징과 정렬을 적용하여 조회 결과를 반환합니다.
   * Querydsl의 contentQuery와 countQuery를 받아 정렬과 오프셋을 적용한 뒤 Page 객체로 반환합니다.
   *
   * @param inDto 페이징 및 정렬 정보를 담고 있는 SearchDto 객체
   * @param sortMap 정렬 가능한 필드와 Querydsl 표현식의 매핑 정보 (화이트리스트)
   * @param contentQuery 데이터를 조회할 Querydsl JPAQuery 객체
   * @param countQuery 데이터의 전체 개수를 조회할 Querydsl JPAQuery 객체
   * @param defaultOrder 정렬 조건이 지정되지 않았을 때 사용할 기본 정렬 조건
   * @param <T> 조회 결과 타입
   * @return 페이징 처리된 조회 결과 (Page 객체)
   */
  protected <T> Page<T> applyPagination(
    SearchDto inDto,
    Map<String, ComparableExpressionBase<?>> sortMap,
    JPAQuery<T> contentQuery,
    JPAQuery<Long> countQuery,
    OrderSpecifier<?> defaultOrder
  ) {
    Pageable pageable = inDto.getPageable();
    
    // 정렬 적용
    OrderSpecifier<?>[] orders = SortHelper.toOrderSpecifiers(
      inDto.getSort(),
      sortMap,
      defaultOrder
    );
    contentQuery.orderBy(orders);
    
    // 페이징 적용 (offset, limit)
    if (pageable.isPaged()) {
      contentQuery.offset(pageable.getOffset())
        .limit(pageable.getPageSize());
    }
    
    // 쿼리 실행
    List<T> content = Objects.requireNonNull(contentQuery.fetch());
    
    // 전체 개수 조회
    Long total = countQuery.fetchOne();
    long safeTotal = (total == null) ? 0L : total;
    
    return new PageImpl<>(content, pageable, safeTotal);
  }
}

