package com.alabtaal.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.alabtaal.library.entity.Auditable;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolumeModel extends Auditable<Long> {

    private Long volumeId;
    private String rowKey;
    private String volumeName;
    private String remarks;
    private Long book;
    private Long rack;
    private List<BookTransLineModel> bookTransLines;
}
