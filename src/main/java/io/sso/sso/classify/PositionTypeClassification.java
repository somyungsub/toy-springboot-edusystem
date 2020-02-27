package io.sso.sso.classify;


public enum PositionTypeClassification {

  ASSISTANT(new String[]{"기초","입문","베이직","기본","초급","BASIC"}),
  ASSISTANT_MANAGER(new String[]{"기초","입문","베이직","기본"}),
  MANAGER(new String[]{"기초","입문","베이직","기본"}),
  DUTY_MANAGER(new String[]{"고급", "활용", "중급", "관리", "응용", "이용", "분석", "설계","아키","실전","실무"}),
  TEAM_READER(new String[]{"기초","입문","베이직","기본"}),

  ;

  private String[] eduKeywords;

  PositionTypeClassification(String[] eduKeywords) {
    this.eduKeywords = eduKeywords;
  }

  public String[] getEduKeywords() {
    return eduKeywords;
  }
}
