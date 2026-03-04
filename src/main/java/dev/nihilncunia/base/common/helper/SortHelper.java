package dev.nihilncunia.base.common.helper;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class SortHelper {
  private SortHelper() {}

  /**
   * 문자열 기반 정렬 파라미터 목록을 {@link OrderSpecifier} 배열로 변환합니다.
   *
   * @param sortList      정렬 토큰 목록(예: ["id,desc", "userName,asc"])
   * @param sortMap       정렬 키 -> Querydsl Expression 매핑(허용 컬럼 화이트리스트)
   * @param defaultOrder  정렬이 없거나 전부 무효일 때 사용할 기본 정렬
   * @return orderBy(...)에 바로 전달 가능한 OrderSpecifier 배열(최소 1개)
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
