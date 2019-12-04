package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.Auditable;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel extends Auditable<Long> {    
    
    private Long bookId;
    
    private String bookName;
    
    private Timestamp publicationDate;
    
    private String bookCondition;
    
    private Byte purchased;
    
    private Long author;
    
    private Long subject;
    
    private Long publisher;
    
    private Long researcher;

    private String remarks;
}
