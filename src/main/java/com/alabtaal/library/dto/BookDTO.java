package com.alabtaal.library.dto;

import com.alabtaal.library.model.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

  private NavigationDtl navigationDtl;
  private BookModel book;
}
