package io.ssosso.edu;


import io.ssosso.classify.DutyTypeClassification;
import io.ssosso.classify.PositionTypeClassification;
import io.ssosso.common.ClassificationLarge;
import io.ssosso.common.ClassificationMiddle;
import io.ssosso.common.ClassificationSmall;
import io.ssosso.common.EduUtils;
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


  public List<Edu> getEduRecommendByUserList(String userId, String dutyType) {

    // TODO 직원정보 얻는 연계
    ///////////////////// 직원정보 임의 선정 //////////////////////////////////////////////////////////////////////
    // 직급
    final PositionTypeClassification assistant = getPositionTypeClassification(userId);

    // 직무
    final DutyTypeClassification developer = getDutyType(dutyType);
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

  private DutyTypeClassification getDutyType(String dutyType) {
    DutyTypeClassification dutyTypeClassification = DutyTypeClassification.DEVELOPER;
    if ("1".equals(dutyType)) {
      dutyTypeClassification = DutyTypeClassification.DEVELOPER;
    } else if ("2".equals(dutyType)) {
      dutyTypeClassification = DutyTypeClassification.SI_DEVELOPER;
    } else if ("3".equals(dutyType)) {
      dutyTypeClassification = DutyTypeClassification.DESIGNER;
    }
    return dutyTypeClassification;
  }

  private PositionTypeClassification getPositionTypeClassification(String userId) {
    PositionTypeClassification positionTypeClassification = PositionTypeClassification.ASSISTANT;
    if ("1".equals(userId)) {
      positionTypeClassification = PositionTypeClassification.ASSISTANT_MANAGER;
    } else if ("2".equals(userId)) {
      positionTypeClassification = PositionTypeClassification.MANAGER;
    } else if ("3".equals(userId)) {
      positionTypeClassification = PositionTypeClassification.DUTY_MANAGER;
    }
    return positionTypeClassification;
  }


  // 교육직무분류판단
  private boolean decideDutyTypeClassification(Edu edu, DutyTypeClassification dutyType) {
    boolean type = decideDutyTypeClassificationSmall(edu, dutyType);
    return type;
  }

  private boolean decideDutyTypeClassificationLarge(Edu edu, DutyTypeClassification dutyType) {
    final ClassificationLarge[] larges = dutyType.getClassificationLarges();
    return EduUtils.isAnyMatchFromArray(larges, large -> large.name().equals(edu.getEduClassificationLarge().getEduClassificationCode()));
  }

  private boolean decideDutyTypeClassificationMiddle(Edu edu, DutyTypeClassification dutyType) {
    final ClassificationMiddle[] middles = dutyType.getClassificationMiddles();
    return EduUtils.isAnyMatchFromArray(middles, middle -> middle.name().equals(edu.getEduClassificationMiddle().getEduClassificationCode()));
  }

  private boolean decideDutyTypeClassificationSmall(Edu edu, DutyTypeClassification dutyType) {
    final ClassificationSmall[] smalls = dutyType.getClassificationSmalls();
    return EduUtils.isAnyMatchFromArray(smalls, small -> small.name().equals(edu.getEduClassificationSmall().getEduClassificationCode()));
  }
}
