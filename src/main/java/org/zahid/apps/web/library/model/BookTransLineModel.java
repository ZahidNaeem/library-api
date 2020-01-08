package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.Auditable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookTransLineModel extends Auditable<Long> {

    private Long lineId;
    private Long volume;
    private Long bookTransHeader;
}
