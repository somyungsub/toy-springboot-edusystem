package io.ssosso.common;

public enum ClassificationMiddle {

  TECH_MANAGEMENT("기술관리"),
  NETWORK("네트워크"),
  COMMON_SKILL("공통스킬"),
  SECURITY("보안"),
  OS("운영체제"),
  SYSTEM("시스템"),
  AI("인공지능"),
  PLANNING("기획"),
  DUTY("직무"),
  DATA_SCIENCE("데이터사이언스"),
  PROGRAMMING("프로그래밍"),
  IOT("사물인터넷"),
  SEMINAR("세미나"),
  SW_ENGINEERING("소프트웨어공학"),
  PROJECT_MANAGEMENT("프로젝트관리"),
  BLOCK_CHAIN("블록체인"),
  FIN_TECH("핀테크"),
  QUALITY("품질"),
  DESIGN("디자인"),
  DATABASE("데이터베이스"),
  CLOUD("클라우드"),
  BIG_DATA("빅데이터"),
  ;
  private String code;

  ClassificationMiddle(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
