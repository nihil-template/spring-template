package dev.nihilncunia.fa_campaign_manager.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum USER_ROLE {
  ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", "슈퍼 관리자", 100),
  ROLE_ADMIN("ROLE_ADMIN", "관리자", 50),
  ROLE_USER("ROLE_USER", "사용자", 10);
  
  private final String roleCd;
  private final String description;
  private final Integer priority;
  
  @JsonValue
  public String getRoleCd() {
    return this.roleCd;
  }
}
