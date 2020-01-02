package org.zahid.apps.web.library.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookResponse {
    private Long bookId;
    private String bookName;
    private Date publicationDate;
    private String bookCondition;
    private Integer purchased;
    private Long authorId;
    private String authorName;
    private Long subjectId;
    private String subjectName;
    private Long publisherId;
    private String publisherName;
    private Long researcherId;
    private String researcherName;
//    private Long shelfId;
//    private String shelfName;
    private String remarks;
}
