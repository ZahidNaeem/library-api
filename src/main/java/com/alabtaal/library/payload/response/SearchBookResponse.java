package com.alabtaal.library.payload.response;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookResponse {

  private UUID id;
  private String bookName;
  private Date publicationDate;
  private String bookCondition;
  private Integer purchased;
  private UUID author;
  private String authorName;
  private UUID subject;
  private String subjectName;
  private UUID publisher;
  private String publisherName;
  private UUID researcher;
  private String researcherName;
  private String remarks;
}
