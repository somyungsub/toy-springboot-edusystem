package io.sso.sso.common;

public enum ClassificationLarge {
  IT("IT"),
  BUSINESS("비즈니스"),
  ;
  private String code;

  ClassificationLarge(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
