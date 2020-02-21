package io.sso.sso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sso.sso.common.EduUtils;
import io.sso.sso.edu.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@JsonTest
//@SpringBootTest
//@TestPropertySource(locations = "classpath:/application.properties")
@TestPropertySource(locations = "classpath:/application-test.properties")
public class UnitTest {

  /*
    기능 확인 단위 테스트
   */
  @Autowired
  ObjectMapper objectMapper;

//  @Autowired
  EduRepository eduRepository;

  private static Path excelPath;

  @Before
  public void setUp() {
//    excelPath = Paths.get("/Users/myungsubso/Desktop/DEV/IntelliJ/tsis-edu-system-analysis/Application/src/test/it_edu_data.xls");
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
//              eduRepository.saveAndFlush(edu);
            }
        );

    List<Edu> collect = saveList.stream()
        .filter(edu -> edu.getEduAgency().getAgencyCode() != null)
        .collect(Collectors.toList());
//
//    collect.forEach(System.out::println);

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

  enum Agency {

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


  enum Completion {
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

    public static EduCompletion createCompletion(String code) {
      return Arrays.stream(Completion.values())
          .filter(completion -> completion.getCode().equals(code))
          .map(completion -> EduCompletion.builder().eduCompletionCode(completion.name()).eduCompletionName(completion.getName()).build())
          .findAny().orElseGet(EduCompletion::new);
    }
  }

  enum ClassificationLarge {
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

    public static EduClassificationLarge createClassificationLarge(String code) {
      return Arrays.stream(ClassificationLarge.values())
          .filter(large -> large.getCode().equals(code))
          .map(large -> EduClassificationLarge.builder().eduClassificationCode(large.name()).eduClassificationName(large.getCode()).build())
          .findAny().orElseGet(EduClassificationLarge::new);
    }

  }

  enum ClassificationMiddle {
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

    public static EduClassificationMiddle createClassificationMiddle(String code) {
      return Arrays.stream(ClassificationMiddle.values())
          .filter(middle -> middle.getCode().equals(code))
          .map(middle -> EduClassificationMiddle.builder().eduClassificationCode(middle.name()).eduClassificationName(middle.getCode()).build())
          .findAny().orElseGet(EduClassificationMiddle::new);
    }
  }

  enum ClassificationSmall {
    PRINCE2("PRINCE2"),
    LICENSE("자격"),
    CompTIA("CompTIA"),
    GENERAL("일반"),
    GO("Go"),
    READERSHIP("리더십"),
    ALIBABA_CLOUD("알리바바 클라우드"),
    IOS("iOS"),
    PRACTICAL_TECH("활용기술"),
    ORACLE("ORACLE"),
    FULL_STACK("풀스택"),
    SERVICE("서비스"),
    AUTOMATION("자동화/관리"),
    JAVA("Java"),
    TECH_REALIZATION("기술구현"),
    DATA("데이터"),
    NETWORK("네트워크"),
    MYSQL("MySQL"),
    MICROSOFT("Microsoft"),
    PROFESSIONAL_ENGINEERING("기술사"),
    FLUTTER("Flutter"),
    DEVICE("디바이스"),
    OPEN_SOURCE_DBMS("오픈소스 DBMS"),
    STRATEGY_PLANNING("전략/기획"),
    IMAGE_PROCESSING("영상처리"),
    MARKETING("마케팅"),
    MODELING("모델링"),
    PERSONAL_GENERAL_AFFAIRS("인사*총무"),
    COMMUNICATION("커뮤니케이션"),
    ENTERPRISE_PLANNING("사업기획"),
    SOLUTION_THINKING("문제해결/사고력"),
    BRAND("브랜드"),
    VIRTUALIZATION("가상화"),
    UI_UX("UI/UX"),
    MSSQL("MS SQL"),
    DESIGN_REALIZATION("설계/구현"),
    CLUSTERING("클러스터링"),
    SWIFT("Swift"),
    DATA_PROCESSING_STORE("데이터 처리/저장"),
    DESIGN("디자인"),
    CLOUD("클라우드"),
    C_SHOP("C#"),
    SYSTEM_NETWORK("시스템/네트워크"),
    PLANNING("기획"),
    R("R"),
    ELASTIC("Elastic"),
    NO_SQL("NoSQL"),
    AGILE("Agile"),
    PHP("PHP"),
    APEX("Apex"),
    DATA_ANALYSIS("데이터 분석"),
    CPE_PDU("CPE/PDU"),
    SPECIAL_LECTURE("특강"),
    EMBEDDED("임베디드"),
    MSP("MSP"),
    KOTLIN("Kotlin"),
    UNIX("Unix"),
    VR_AR("VR/AR"),
    ORDERING_MANAGEMENT("발주관리"),
    CHAT_BOT("챗봇"),
    AUDIT("감사"),
    SUPERVISION("감리"),
    HTML("HTML"),
    APPLICATION("어플리케이션"),
    DATA_STRUCTURE_ALGORITHM("자료구조/알고리즘"),
    PLATFORM("플랫폼"),
    METHODOLOGY("방법론"),
    DEVELOPMENT("개발"),
    BUILD_MANAGE("구축/운영"),
    MOCK_HACK("모의해킹"),
    ML_DL("머신러닝/딥러닝"),
    JAVASCRIPT("Javascript"),
    DOT_NET(".NET"),
    PYTHON("Python"),
    AZURE("Azure"),
    ARCHITECTURE("Architecture"),
    NEXACRO("넥사크로"),
    HYBRID("Hybrid"),
    ANALYSIS_DESIGN("분석/설계"),
    FINANCIAL_ACCOUNT_AFFAIRS("재무/회계"),
    ITIL("ITIL"),
    DOCUMENT_WRITE("문서작성"),
    SQL_TUNING("SQL/튜닝"),
    PROGRAMMING("프로그래밍"),
    android("Android"),
    RISK_MANAGEMENT("리스크관리"),
    DEV_OPS("DevOps"),
    LINUX("Linux"),
    MANAGEMENT("관리"),
    CERT_MALICIOUS_CODE("침해대응/악성코드"),
    REDHAT("RedHat"),
    DATA_AGGREGATION("데이터 수집"),
    SAP("SAP"),
    CROSS_PLATFORM("크로스 플랫폼"),
    SAS("SAS"),
    OPEN_STACK("OpenStack"),
    TEST("테스트"),
    C_CPP("C/C++"),
    SHELL("Shell"),
    OA("OA"),
    FIN_TECH("핀테크"),
    SCRUM("Scrum"),
    WEB_WAS("WEB/WAS"),
    AWS("AWS"),
    PUBLISHING("퍼블리싱"),
    SALES_FORCE("Salesforce"),
    PERFORMANCE_TUNING("성능튜닝"),
    SCALA("Scala"),
    SOLUTION("솔루션"),
    CONSULTING_LAW_AUDIT("컨설팅/법/감사"),
    DESIGN_TOOL("디자인툴"),
    PMP("PMP"),
    SW_CODING("SW코딩"),
    BUSINESS("영업"),
    MS_WINDOWS("MS Windows"),
    ;

    private String code;

    ClassificationSmall(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}
