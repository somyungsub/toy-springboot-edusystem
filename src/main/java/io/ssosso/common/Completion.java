package io.ssosso.common;

public enum Completion {

  BASIC("ET001", "기본교육"),
  PROFESSIONAL("ET002", "전문교육"),
  OPTION("ET003", "선택교육"),
  ;

  private String code;
  private String name;

  Completion(String code, String name) {
    this.code = code;
    this.name = name;
  }


  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }
}
