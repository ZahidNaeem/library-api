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
public class PublisherModel extends Auditable<Long> {
    
    private Long publisherId;
    
    private String publisherName;

    private String remarks;
    
    private List<BookModel> books;
}
