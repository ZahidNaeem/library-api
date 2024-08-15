package com.alabtaal.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alabtaal.library.entity.Auditable;

import java.util.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransHeaderModel extends Auditable<Long> {
    private Long headerId;

    private String transType;

    private Date transDate;

    private Long reader;

    private String remarks;

    private List<BookTransLineModel> bookTransLines;
}
