package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
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
public class ResearcherModel extends Auditable<String> {

  private UUID id;

  private String name;

  private String remarks;

  private Set<BookModel> books;
}
