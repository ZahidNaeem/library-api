package com.alabtaal.library.dto;

import com.alabtaal.library.model.BookTransHeaderModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransHeaderDTO {

  private NavigationDtl navigationDtl;
  private BookTransHeaderModel bookTransHeader;
}
