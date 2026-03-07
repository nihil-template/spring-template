package dev.nihilncunia.fa_campaign_manager.common.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 슈퍼 관리자 권한(ROLE_SUPER_ADMIN)이 있는 사용자만 접근할 수 있도록 설정합니다.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('SUPER_ADMIN')")
public @interface IsSuperAdmin {
}
