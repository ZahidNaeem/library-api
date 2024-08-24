package com.alabtaal.library.payload.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListWithPagination<T> {

  private List<T> list;
  private int pageSize;
  private int pageNumber;
  private int totalPages;
  private int totalPageRecords;
  private long totalRecords;
}

