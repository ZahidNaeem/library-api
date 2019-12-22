package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.Auditable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel extends Auditable<Long> {

    private Long bookId;

    private String bookName;

    private Date publicationDate;

    private String bookCondition;

    private Integer purchased;

    private String remarks;

    private List<VolumeModel> volumes;

    private Long author;

    private Long subject;

    private Long publisher;

    private Long researcher;

    private Long shelf;
}
