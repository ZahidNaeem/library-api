package com.alabtaal.library.util;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.payload.response.ListWithPagination;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ListToPageConverter {

  public static <T> ListWithPagination<T> convert(
      final List<T> list,
      final Integer pageNumber,
      final Integer pageSize,
      final String sortBy,
      final String sortDirection,
      final Class<T> clazz) throws BadRequestException {
    Pageable pageable = Miscellaneous.handlePaginationValues(pageNumber, pageSize, sortBy, sortDirection, clazz);
    int start = (int) pageable.getOffset();
    int end = Math.min((start + pageable.getPageSize()), list.size());
    final PageImpl<T> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

    return ListWithPagination
        .<T>builder()
        .list(page.getContent())
        .pageSize(page.getSize())
        .pageNumber(page.getNumber())
        .totalPages(page.getTotalPages())
        .totalPageRecords(page.getNumberOfElements())
        .totalRecords(page.getTotalElements())
        .build();
  }
}
