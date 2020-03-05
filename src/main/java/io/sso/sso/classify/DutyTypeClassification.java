package io.sso.sso.classify;


import io.sso.sso.common.ClassificationLarge;
import io.sso.sso.common.ClassificationMiddle;
import io.sso.sso.common.ClassificationSmall;

public enum DutyTypeClassification {

  DEVELOPER(
      new ClassificationLarge[]
          {
              ClassificationLarge.IT
          }
      ,
      new ClassificationMiddle[]
          {
              ClassificationMiddle.DATABASE,
              ClassificationMiddle.PROGRAMMING,
          }
      ,
      new ClassificationSmall[]
          {
              ClassificationSmall.APPLICATION,
              ClassificationSmall.JAVA,
              ClassificationSmall.ANDROID,
              ClassificationSmall.C_CPP,
              ClassificationSmall.FULL_STACK
          }
  ),
  SI_DEVELOPER(
      new ClassificationLarge[]
          {
              ClassificationLarge.IT
          }
      ,
      new ClassificationMiddle[]
          {
              ClassificationMiddle.DATABASE,
              ClassificationMiddle.PROGRAMMING,
              ClassificationMiddle.SW_ENGINEERING
          }
      ,
      new ClassificationSmall[]
          {
              ClassificationSmall.APPLICATION,
              ClassificationSmall.JAVA,
              ClassificationSmall.FULL_STACK,
              ClassificationSmall.DATA_STRUCTURE_ALGORITHM,
              ClassificationSmall.DEVELOPMENT,
              ClassificationSmall.HTML,
              ClassificationSmall.TEST,
              ClassificationSmall.MSSQL,
              ClassificationSmall.MYSQL,
              ClassificationSmall.NO_SQL,
              ClassificationSmall.SQL_TUNING
          }
  ),
//  DESIGNER,
//  SECURITY,
//  NETWORK,
//  SERVER,
//  MIDDLE_WARE,

  ;

  private ClassificationLarge[] classificationLarges;
  private ClassificationMiddle[] classificationMiddles;
  private ClassificationSmall[] classificationSmalls;

  DutyTypeClassification(ClassificationLarge[] classificationLarges, ClassificationMiddle[] classificationMiddles, ClassificationSmall[] classificationSmalls) {
    this.classificationLarges = classificationLarges;
    this.classificationMiddles = classificationMiddles;
    this.classificationSmalls = classificationSmalls;
  }



  public ClassificationLarge[] getClassificationLarges() {
    return classificationLarges;
  }

  public ClassificationMiddle[] getClassificationMiddles() {
    return classificationMiddles;
  }

  public ClassificationSmall[] getClassificationSmalls() {
    return classificationSmalls;
  }
}
