package io.ssosso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ssosso.common.*;
import io.ssosso.edu.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
//@JsonTest
@SpringBootTest
@TestPropertySource(locations = "classpath:/application.properties")
//@TestPropertySource(locations = "classpath:/application-test.properties")
public class UnitTest {

  /*
    기능 확인 단위 테스트
   */
  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  EduRepository eduRepository;

  private static Path excelPath;

  @Before
  public void setUp() {
//    excelPath = Paths.get("/Users/myungsubso/Desktop/DEV/IntelliJ/toy-springboot-edusystem/src/test/resources/it_edu_data.xls");
  }

  @Test
  public void test() throws JsonProcessingException {
    final List<Edu> eduList = Arrays.asList(
        new Edu().builder().eduId(1L).eduName("java1").build(),
        new Edu().builder().eduId(2L).eduName("java2").build(),
        new Edu().builder().eduId(3L).eduName("java3").build(),
        new Edu().builder().eduId(4L).eduName("java4").build()
    );

    final String s = objectMapper.writeValueAsString(eduList);
    System.out.println(s);
  }

  //  @Test
  private void 엑셀업로드() throws IOException {
    final Workbook sheets = WorkbookFactory.create(excelPath.toFile());
    final Sheet sheet = sheets.getSheetAt(1);
    final Stream<Row> rowStream = EduUtils.getStream(sheet.rowIterator());

    rowStream
        .map(cells -> EduUtils.getStream(cells.cellIterator())
            .map(cell -> cell.getStringCellValue().replaceAll(System.lineSeparator(), "")))
        .forEach(s -> System.out.println(s.collect(Collectors.joining(","))));

  }

  //  @Test
  private void 코드값추출() throws IOException {

    // 리스트 변환
    final List<List<String>> collect = EduUtils.extractExcelToList(1, excelPath);

    // 코드 추출 (0:교육기관, 3:과정분류_대, 4:과정분류_중, 5:과정분류_소)
    collect.stream().map(strings -> strings.get(0)).collect(Collectors.toSet()).forEach(System.out::println);
    collect.stream().map(strings -> strings.get(3)).collect(Collectors.toSet()).forEach(System.out::println);
    collect.stream().map(strings -> strings.get(4)).collect(Collectors.toSet()).forEach(System.out::println);
    collect.stream().map(strings -> strings.get(5)).collect(Collectors.toSet()).forEach(System.out::println);

  }


  //  @Test
  private void 코드만들기() throws IOException {
    int sheetIndex = 1;
    final List<List<String>> lists = EduUtils.extractExcelToList(sheetIndex, excelPath);

    // 추출 함수
    System.out.println("기관코드 추출");
//    lists.stream().forEach(strings -> System.out.println("(\"" + strings.get(1) + "\",\"" + strings.get(0) + "\"),"));

    System.out.println("===== 대 =====");
    lists.stream().map(strings -> strings.get(3)).collect(Collectors.toSet()).forEach(s -> System.out.println("(\"" + s + "\"),"));
    System.out.println("===== 중 =====");
    lists.stream().map(strings -> strings.get(4)).collect(Collectors.toSet()).forEach(s -> System.out.println("(\"" + s + "\"),"));
    System.out.println("===== 소 =====");
    lists.stream().map(strings -> strings.get(5)).collect(Collectors.toSet()).forEach(s -> System.out.println("(\"" + s + "\"),"));

  }

  // 차후 적재 필요시 사용
//  @Test
  private void 엑셀데이터적재() throws Exception {
    final List<List<String>> lists = EduUtils.extractExcelToList(1, excelPath);
    List<Edu> saveList = new ArrayList<>();
    AtomicReference<Long> id = new AtomicReference<>(1L);
    lists.stream()
        .forEach(strings -> {
              String 기관코드 = strings.get(0);
              String 교육명 = strings.get(2);
              String 과정분류_대 = strings.get(3);
              String 과정분류_중 = strings.get(4);
              String 과정분류_소 = strings.get(5);
              String 교육이수구분코드 = strings.get(6);

              Edu edu
                  = Edu.builder()
                  .eduId(id.getAndSet(id.get() + 1))
                  .eduName(교육명)
                  .curriculumYear(2019)
                  .eduAgency(
                      EduUtils.createEduCode(
                          Agency.values(),
                          (Agency agency) -> agency.getCode().equals(기관코드),
                          (Agency agency) -> EduAgency.builder().agencyCode(agency.name()).agencyName(agency.getName()).build(),
                          EduAgency::new
                      )
                  )
                  .eduClassificationLarge(
                      EduUtils.createEduCode(
                          ClassificationLarge.values(),
                          (ClassificationLarge c) -> c.getCode().equals(과정분류_대),
                          (ClassificationLarge c) -> EduClassificationLarge.builder()
                              .eduClassificationCode(c.name()).eduClassificationName(c.getCode()).build(),
                          EduClassificationLarge::new
                      )
                  )
                  .eduClassificationMiddle(
                      EduUtils.createEduCode(
                          ClassificationMiddle.values(),
                          (ClassificationMiddle c) -> c.getCode().equals(과정분류_중),
                          (ClassificationMiddle c) -> EduClassificationMiddle.builder()
                              .eduClassificationCode(c.name()).eduClassificationName(c.getCode()).build(),
                          EduClassificationMiddle::new
                      )
                  )
                  .eduClassificationSmall(
                      EduUtils.createEduCode(
                          ClassificationSmall.values(),
                          (ClassificationSmall c) -> c.getCode().equals(과정분류_소),
                          (ClassificationSmall c) -> EduClassificationSmall.builder()
                              .eduClassificationCode(c.name()).eduClassificationName(c.getCode()).build(),
                          EduClassificationSmall::new
                      )
                  )
                  .eduCompletion(
                      EduUtils.createEduCode(
                          Completion.values(),
                          (Completion c) -> c.getCode().equals(교육이수구분코드),
                          (Completion c) -> EduCompletion.builder()
                              .eduCompletionCode(c.name()).eduCompletionName(c.getName()).build(),
                          EduCompletion::new
                      )
                  )
                  .build();
              saveList.add(edu);
            }
        );

    List<Edu> collect = saveList.stream()
        .filter(edu -> edu.getEduAgency().getAgencyCode() != null)
        .collect(Collectors.toList());

    // 적재
    eduRepository.saveAll(collect);

//    saveList.stream()
//        .filter(edu -> edu.getEduAgency().getAgencyCode() == null)
//        .filter(edu -> edu.getEduName() == null || edu.getEduName().isEmpty())
//        .filter(edu -> edu.getEduClassificationLarge().getEduClassificationCode() == null)
//        .filter(edu -> edu.getEduClassificationMiddle().getEduClassificationCode() == null)
//        .filter(edu -> edu.getEduClassificationSmall().getEduClassificationCode() == null)
//        .filter(edu -> edu.getEduCompletion().getEduCompletionCode() == null)
//        .forEach(edu -> System.out.println(edu));

  }
}