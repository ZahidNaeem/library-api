package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zahid.apps.web.library.entity.Auditable;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RackModel extends Auditable<Long> {

    private Long rackId;
    private String rowKey;
    private String rackName;
    private String remarks;
    private List<VolumeModel> volumes;
    private Long shelf;
}
