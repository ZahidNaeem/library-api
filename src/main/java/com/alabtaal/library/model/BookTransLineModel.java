package com.alabtaal.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alabtaal.library.entity.Auditable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransLineModel extends Auditable<Long> {

    private Long lineId;
    private String rowKey;
    private Long book;
    private Long volume;
    private Long bookTransHeader;
}
