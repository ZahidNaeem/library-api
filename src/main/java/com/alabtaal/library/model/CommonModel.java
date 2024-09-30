package com.alabtaal.library.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonModel {

  private UUID id;
  private String name;
}
