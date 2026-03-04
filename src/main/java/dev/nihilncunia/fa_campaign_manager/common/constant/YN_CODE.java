package dev.nihilncunia.fa_campaign_manager.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum YN_CODE {
  /** 사용/활성/삭제됨 (Yes) */
  Y("Y"),
  /** 미사용/비활성/삭제안됨 (No) */
  N("N");
  
  private final String code;
  
  /**
   * JSON 변환 시 이름(Y/N)을 반환합니다.
   * @return Y 또는 N 문자열
   */
  @JsonValue
  public String getCode() {
    return this.name();
  }
}
