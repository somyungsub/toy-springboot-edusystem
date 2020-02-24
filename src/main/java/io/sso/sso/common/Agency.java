package io.sso.sso.common;

public enum Agency {

  MULTI_CAMPUS("EI001", "멀티캠퍼스"),
  KOREA_SW_TECH("EI002", "한국소프트웨어기술진흥협회"),
  KOREA_INFO_SECURE("EI003", "한국정보보호교육센터"),
  ORACLE_JAVA("EI004", "오라클자바교육센터"),
  BIT("EI005", "비트 교육센터"),
  KOREA_INFO_TECH("EI006", "한국정보기술연구원"),
  IT_WILL("EI007", "아이티윌"),
  LYZEUM("EI008", "라이지움"),
  HP("EI009", "HP교육센터"),
  CHOONGANG_INFO_PROCESSING("EI010", "중앙정보처리학원"),
  FAST_LANE("EI011", "패스트레인 정보통신 학원"),
  FAST_CAMPUS("EI012", "패스트 캠퍼스"),
  KOREA_IT_ACADEMY("EI013", "코리아IT아카데미"),
  ;
  private String code;
  private String name;

  Agency(String code, String name) {
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
