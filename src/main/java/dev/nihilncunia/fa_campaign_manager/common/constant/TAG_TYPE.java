package dev.nihilncunia.fa_campaign_manager.common.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TAG_TYPE {
  STRUCTURE_DOMAIN("STRUCTURE_DOMAIN", "구조-권역"),
  STRUCTURE_SYSTEM("STRUCTURE_SYSTEM", "구조-계통"),
  STRUCTURE_FORM("STRUCTURE_FORM", "구조-형태"),
  WEAPON_PERSONAL("WEAPON_PERSONAL", "무기-휴대 무장"),
  WEAPON_PLACED("WEAPON_PLACED", "무기-거치 화기"),
  WEAPON_TACTICAL("WEAPON_TACTICAL", "무기-전술 병기"),
  ATTRIBUTE_DAMAGE("ATTRIBUTE_DAMAGE", "속성"),
  STATUS_TEMPORARY("STATUS_TEMPORARY", "일시적 상태이상"),
  STATUS_PERMANENT("STATUS_PERMANENT", "영구적 상태이상"),
  GENERAL_TAG("GENERAL_TAG", "일반 태그"),
  GRADE_TIER("GRADE_TIER", "위계"),
  GRADE_CIRCLE("GRADE_CIRCLE", "서클");
  
  private final String name;
  private final String korName;
  
  @JsonValue
  public String getName() {
    return this.name;
  }
}
