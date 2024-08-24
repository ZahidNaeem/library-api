package com.alabtaal.library.dto;

import com.alabtaal.library.model.ResearcherModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearcherDTO {

  private NavigationDtl navigationDtl;
  private ResearcherModel researcher;
}
