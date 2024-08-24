package com.alabtaal.library.dto;

import com.alabtaal.library.model.AuthorModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

  private NavigationDtl navigationDtl;
  private AuthorModel author;
}
