package com.alabtaal.library.dto;

import com.alabtaal.library.model.ShelfModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfDTO {

  private NavigationDtl navigationDtl;
  private ShelfModel shelf;
}
