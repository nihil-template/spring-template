package dev.nihilncunia.fa_campaign_manager.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExampleExclude {
  /** 제외할 응답 예시의 키 배열 */
  String[] keys() default {};
}
