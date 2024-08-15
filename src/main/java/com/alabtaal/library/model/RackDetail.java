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
public class RackDetail extends Auditable<Long> {

    private Long rackId;
    private String rackName;
    private String remarks;
//    private List<VolumeModel> volumes;
    private Long shelfId;
    private String shelfName;
}