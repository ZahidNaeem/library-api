package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import com.alabtaal.library.enumeration.BookCondition;
import com.alabtaal.library.enumeration.BookSource;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;
import java.util.List;
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
public class BookModel extends Auditable<String> {

  private UUID id;

  private String name;

  private Date publicationDate;

  @Enumerated(value = EnumType.STRING)
  private BookCondition bookCondition;

  @Enumerated(value = EnumType.STRING)
  private BookSource bookSource;

  private String remarks;

  private List<VolumeModel> volumes;

  private UUID author;

  private String authorName;

  private UUID subject;

  private String subjectName;

  private UUID publisher;

  private String publisherName;

  private UUID researcher;

  private String researcherName;

  private UUID shelf;

  private String shelfName;
}
