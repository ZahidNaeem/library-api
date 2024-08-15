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
public class ShelfModel extends Auditable<Long> {

    private Long shelfId;

    private String shelfName;

    private String remarks;

    private List<BookModel> books;

    private List<RackModel> racks;
}
