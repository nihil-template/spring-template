package dev.nihilncunia.fa_campaign_manager.common.annotation;

import dev.nihilncunia.fa_campaign_manager.common.constant.SEARCH_TYPE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchCondition {
  /** 검색 비교 타입 (EQUALS, LIKE 등) */
  SEARCH_TYPE type() default SEARCH_TYPE.EQUALS;
  /** 엔티티 내 실제 매핑될 필드 이름 (기본값은 DTO 필드명) */
  String fieldName() default "";
}
