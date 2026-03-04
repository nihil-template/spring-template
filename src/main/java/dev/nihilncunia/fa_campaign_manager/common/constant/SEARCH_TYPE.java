package dev.nihilncunia.fa_campaign_manager.common.constant;

public enum SEARCH_TYPE {
  EQUALS("EQUALS"),
  LIKE("LIKE"),
  LEFT_LIKE("LEFT_LIKE"),
  RIGHT_LIKE("RIGHT_LIKE"),
  GREATER_THAN("GREATER_THAN"),
  GREATER_THAN_OR_EQUAL("GREATER_THAN_OR_EQUAL"),
  LESS_THAN("LESS_THAN"),
  LESS_THAN_OR_EQUAL("LESS_THAN_OR_EQUAL"),
  BETWEEN("BETWEEN");

  private final String type;

  SEARCH_TYPE(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
