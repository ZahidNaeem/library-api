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
public class RackModel extends Auditable<Long> {

    private Long rackId;
    private String rackName;
    private String remarks;
    private Long shelf;
}
