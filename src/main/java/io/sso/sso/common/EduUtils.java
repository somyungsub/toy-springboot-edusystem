package io.sso.sso.common;

import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EduUtils {

  /**
   * Iterator -> Stream 변환
   *
   * @param iterator iterator
   * @param <T>      iterator 실행 할 타입
   * @return         변환된 stream
   */
  public static <T> Stream<T> getStream(Iterator<? extends T> iterator) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
  }


  /**
   * 엑셀 리스트 추출
   * 엑셀 행,열 (2차 행렬) -> 이중 List 형태로 변환
   * @param sheetIndex    엑셀파일에서 sheet의 번호 (ex 첫번쨰 0, 두번째 1, ...)
   * @param path          파일의 경로
   * @return              이중 List로 변환한 데이터
   * @throws IOException  파일을 못찾는 경우 등 IOException
   */
  public static List<List<String>> extractExcelToList(int sheetIndex, Path path) throws IOException {
    final Workbook sheets = WorkbookFactory.create(path.toFile());
    final Sheet sheet = sheets.getSheetAt(sheetIndex);
    final Stream<Row> rowStream = EduUtils.getStream(sheet.rowIterator());


    // 리스트 변환 및 반환
    return rowStream
        .map(cells -> EduUtils.getStream(cells.cellIterator())
            .map(Cell::getStringCellValue)
            .collect(Collectors.toList())
        )
        .collect(Collectors.toList());
  }


  /**
   * 주목적은 코드변환으로 사용. 이외 메서드 형식에 맞게 활용
   *
   * @param array       Array -> Stream 으로 만들 배열
   * @param predicate   Array에서 Filtering 할 함수 (Predicate)
   * @param mapper      Filtering -> Mapper 로 변환 시킬 함수 (Function)
   * @param supplier    Filtering이 없는 경우 NullSafe 를 위한 supplier 함수
   * @param <T>         Array 타입
   * @param <E>         Mapping 타입
   * @return            발견된 E 반환, 발견되지 않은 경우 supplier 함수를 통해 반환
   */
  public static <T, E> E createEduCode(T[] array, Predicate<T> predicate,
                                       Function<T, E> mapper, Supplier<E> supplier) {

    E e = null;

    try {
      e = Arrays.stream(array)
          .filter(o -> predicate.test(o))
          .map(t -> mapper.apply(t))
          .findAny().orElseGet(supplier);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return e;
  }


  /**
   * Array에서 한 건이라도 발견 되면 true를 반환하는 메서드
   *
   * @param array       판단하기 위한 Array
   * @param predicate   Filtering 을 위한 함수 (Predicate)
   * @param <T>         Array 타입
   * @return            발견되면 true, 아니면 false
   */
  public static <T> boolean isAnyMatchFromArray(T[] array, Predicate<T> predicate) {
    return Arrays.stream(array).anyMatch(predicate);
  }
}

