package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
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
public class SubjectModel extends Auditable<String> {

  private UUID id;

  private String code;

  private String name;

  private UUID parentSubject;

  private String parentSubjectName;

  private String remarks;

  private List<BookModel> books;

  private List<SubjectModel> subjects;
}
