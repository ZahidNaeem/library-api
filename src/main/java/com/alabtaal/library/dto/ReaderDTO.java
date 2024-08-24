package com.alabtaal.library.dto;

import com.alabtaal.library.model.ReaderModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {

  private NavigationDtl navigationDtl;
  private ReaderModel reader;
}
