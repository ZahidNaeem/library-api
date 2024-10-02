package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import com.alabtaal.library.enumeration.BookCondition;
import com.alabtaal.library.enumeration.BookSource;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

  @NotNull
  private Long bookNumber;

  private Date publicationDate;

  private Integer edition;

  @Enumerated(value = EnumType.STRING)
  private BookCondition bookCondition;

  @Enumerated(value = EnumType.STRING)
  private BookSource bookSource;

  private String remarks;

  private List<VolumeModel> volumes;

  private Set<CommonModel> authors;

  private Set<CommonModel> subjects;

  private UUID publisher;

  private String publisherName;

  private Set<CommonModel> researchers;
}
