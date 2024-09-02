package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import com.alabtaal.library.enumeration.BookCondition;
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
public class BookModel extends Auditable<String> {

  private UUID id;

  private String name;

  private Date publicationDate;

  @Enumerated(value = EnumType.STRING)
  private BookCondition bookCondition;

  private Integer purchased;

  private String remarks;

  private List<VolumeModel> volumes;

  private List<BookTransLineModel> bookTransLines;

  private UUID author;

  private UUID subject;

  private UUID publisher;

  private UUID researcher;

  private UUID shelf;
}
