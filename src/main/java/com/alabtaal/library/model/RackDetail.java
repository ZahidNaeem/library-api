package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackDetail extends Auditable<String> {

  private UUID id;
  private String rackName;
  private String remarks;
  private UUID shelf;
  private String shelfName;
}