package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import org.zahid.apps.web.library.entity.Auditable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearcherModel extends Auditable<Long> {
    
    private Long researcherId;
    
    private String researcherName;

    private String remarks;
    
    private List<BookModel> books;
}
