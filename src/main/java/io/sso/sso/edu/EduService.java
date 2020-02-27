package io.sso.sso.edu;


import io.sso.sso.classify.DutyTypeClassification;
import io.sso.sso.classify.PositionTypeClassification;
import io.sso.sso.common.ClassificationLarge;
import io.sso.sso.common.ClassificationMiddle;
import io.sso.sso.common.ClassificationSmall;
import io.sso.sso.common.EduUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduService {
  @Autowired
  EduRepository eduRepository;

  @Autowired
  List<Edu> eduAllList;

  public List<Edu> getEduRecommendAll() {
    return eduRepository.findAll();
  }


  public List<Edu> getEduRecommendByUserList(String userId) {

    // TODO 직원정보 얻는 연계
    ///////////////////// 직원정보 임의 선정 //////////////////////////////////////////////////////////////////////
    // 직무
    final DutyTypeClassification developer = DutyTypeClassification.DEVELOPER;

    // 직급
    final PositionTypeClassification assistant = PositionTypeClassification.ASSISTANT;
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    final List<Edu> eduDutyList
        = eduAllList.stream()
        .filter(edu -> this.decideDutyTypeClassification(edu, developer))
        .collect(Collectors.toList());

    return eduDutyList.stream()
        .filter(edu -> EduUtils.isAnyMatchFromArray(
            assistant.getEduKeywords(), s -> edu.getEduName().contains(s)
            )
        )
        .collect(Collectors.toList());
  }

  // 교육직무분류판단
  private boolean decideDutyTypeClassification(Edu edu, DutyTypeClassification dutyType) {

//    final ClassificationLarge[] larges = dutyType.getClassificationLarges();
//    final ClassificationMiddle[] middles = dutyType.getClassificationMiddles();
    final ClassificationSmall[] smalls = dutyType.getClassificationSmalls();

//    final boolean largeType
//        = EduUtils.isAnyMatchFromArray(larges, large -> large.name().equals(edu.getEduClassificationLarge().getEduClassificationCode()));
//
//    final boolean middleType
//        = EduUtils.isAnyMatchFromArray(middles, middle -> middle.name().equals(edu.getEduClassificationMiddle().getEduClassificationCode()));

    final boolean smallType
        = EduUtils.isAnyMatchFromArray(smalls, small -> small.name().equals(edu.getEduClassificationSmall().getEduClassificationCode()));

//    return largeType && middleType ;
    return smallType;
  }
}
