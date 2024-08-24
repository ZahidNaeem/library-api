package com.alabtaal.library.dto;

import com.alabtaal.library.model.SubjectModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {

  private NavigationDtl navigationDtl;
  private SubjectModel subject;
}
