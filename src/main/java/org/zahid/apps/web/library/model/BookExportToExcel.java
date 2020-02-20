package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookExportToExcel {
    private String bookName;
    private Date publicationDate;
    private String bookCondition;
    private String purchased;
    private String author;
    private String subject;
    private String publisher;
    private String researcher;
    private String remarks;
    private Integer volumes;
}
