package io.sso.sso.edu;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EduRepositoryTest {

  @Autowired
  EduRepository eduRepository;

  List<Edu> list;

  @Before
  public void setUp() {
    list = Arrays.asList(
        new Edu(1L, "빅데이터 MongoDB 심화", LocalDateTime.now().getYear(),
            EduAgency.builder().agencyCode("MULTI_CAMPUS").agencyName("멀티캠퍼스").build(),
            EduClassificationLarge.builder().eduClassificationCode("IT").eduClassificationName("IT").build(),
            EduClassificationMiddle.builder().eduClassificationCode("DATA_SCIENCE").eduClassificationName("데이터사이언스").build(),
            EduClassificationSmall.builder().eduClassificationCode("DATA_COLLECTION").eduClassificationName("데이터수집").build(),
            EduCompletion.builder().eduCompletionCode("PROFESSIONAL").eduCompletionName("전문교육").build()),
        new Edu(2L, "빅데이터 MongoDB 분석", LocalDateTime.now().getYear(),
            EduAgency.builder().agencyCode("MULTI_CAMPUS").agencyName("멀티캠퍼스").build(),
            EduClassificationLarge.builder().eduClassificationCode("IT").eduClassificationName("IT").build(),
            EduClassificationMiddle.builder().eduClassificationCode("DATA_SCIENCE").eduClassificationName("데이터사이언스").build(),
            EduClassificationSmall.builder().eduClassificationCode("DATA_ANALYSIS").eduClassificationName("데이터분석").build(),
            EduCompletion.builder().eduCompletionCode("PROFESSIONAL").eduCompletionName("전문교육").build()),
        new Edu(3L, "네트워크 자바 프로그래밍", LocalDateTime.now().getYear(),
            EduAgency.builder().agencyCode("MULTI_CAMPUS").agencyName("멀티캠퍼스").build(),
            EduClassificationLarge.builder().eduClassificationCode("IT").eduClassificationName("IT").build(),
            EduClassificationMiddle.builder().eduClassificationCode("PROGRAMMING").eduClassificationName("프로그래밍").build(),
            EduClassificationSmall.builder().eduClassificationCode("NETWORK").eduClassificationName("네트워크").build(),
            EduCompletion.builder().eduCompletionCode("PROFESSIONAL").eduCompletionName("전문교육").build())
    );
  }

  @Test
  public void insert() {
    final List<Edu> edus = eduRepository.saveAll(list);

    assertThat(eduRepository.findById(1L).get().getEduName()).isEqualTo(edus.get(0).getEduName());
    assertThat(eduRepository.findById(2L).get().getEduName()).isEqualTo(edus.get(1).getEduName());
    assertThat(eduRepository.findById(3L).get().getEduName()).isEqualTo(edus.get(2).getEduName());
  }

  @Test
  public void findAll() {
    eduRepository.findAll().forEach(System.out::println);
  }

  @Test
  public void test(){

  }



}