package dev.nihilncunia.fa_campaign_manager.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class CurrentUserProvider {

  private CurrentUserProvider() {
  }

  /**
   * 현재 인증된 사용자의 ID를 조회합니다.
   * @return 인증된 사용자 ID (없으면 Optional.empty())
   */
  public static Optional<Long> getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
        || "anonymousUser".equals(authentication.getPrincipal())) {
      return Optional.empty();
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof AppUserPrincipal appUserPrincipal) {
      return Optional.ofNullable(appUserPrincipal.getId());
    }

    return Optional.empty();
  }
}
