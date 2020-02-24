package io.sso.sso.common;

import io.sso.sso.classify.DutyTypeClassification;
import io.sso.sso.edu.Edu;
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

  // Iterator -> Stream 변환
  public static <T> Stream<T> getStream(Iterator<? extends T> iterator) {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
  }

  // 엑셀 리스트 추출
  public static List<List<String>> extractExcelToList(int sheetIndex, Path path) throws IOException {
    final Workbook sheets = WorkbookFactory.create(path.toFile());
    final Sheet sheet = sheets.getSheetAt(sheetIndex);
    final Stream<Row> rowStream = EduUtils.getStream(sheet.rowIterator());


    // 리스트 변환 및 반환
    return rowStream
        .map(cells -> getStream(cells.cellIterator())
            .map(Cell::getStringCellValue)
            .collect(Collectors.toList())
        )
        .collect(Collectors.toList());
  }

  // 코드변환
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

  // 교육직무분류판단
  public static boolean decideDutyTypeClassification(Edu edu, DutyTypeClassification dutyType) {

    final ClassificationLarge[] larges = dutyType.getClassificationLarges();
    final ClassificationMiddle[] middles = dutyType.getClassificationMiddles();
    final ClassificationSmall[] smalls = dutyType.getClassificationSmalls();

    final boolean largeType
        = isAnyMatchFromArray(larges, large -> large.name().equals(edu.getEduClassificationLarge().getEduClassificationCode()));

    final boolean middleType
        = isAnyMatchFromArray(middles, middle -> middle.name().equals(edu.getEduClassificationMiddle().getEduClassificationCode()));

    final boolean smallType
        = isAnyMatchFromArray(smalls, small -> small.name().equals(edu.getEduClassificationSmall().getEduClassificationCode()));

    return largeType && middleType && smallType;
  }

  public static <T> boolean isAnyMatchFromArray(T[] array, Predicate<T> predicate) {
    return Arrays.stream(array).anyMatch(predicate);
  }



}

