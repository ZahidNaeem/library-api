package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransLineModel extends Auditable<String> {

  private UUID id;
  private UUID volume;
  private String volumeName;
  private Date issuanceDate;
  private Date receiptDate;
  private String remarks;
  private UUID bookTransHeader;
}
