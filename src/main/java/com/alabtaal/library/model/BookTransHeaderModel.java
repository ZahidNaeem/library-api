package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import com.alabtaal.library.enumeration.TransType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;
import java.util.List;
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
public class BookTransHeaderModel extends Auditable<String> {

  private UUID id;

  @Enumerated(value = EnumType.STRING)
  private TransType transType;

  private Date transDate;

  private UUID reader;

  private String remarks;

  private List<BookTransLineModel> bookTransLines;
}
