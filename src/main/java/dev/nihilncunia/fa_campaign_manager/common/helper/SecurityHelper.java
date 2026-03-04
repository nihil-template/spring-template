package dev.nihilncunia.fa_campaign_manager.common.helper;

import dev.nihilncunia.fa_campaign_manager.common.security.CurrentUserProvider;

import java.util.Optional;

public class SecurityHelper {

  /**
   * 현재 인증된 사용자의 ID를 조회합니다.
   * @return 인증된 사용자의 ID (인증 정보가 없을 경우 Optional.empty())
   */
  public static Optional<Long> getCurrentUserId() {
    return CurrentUserProvider.getCurrentUserId();
  }
}
