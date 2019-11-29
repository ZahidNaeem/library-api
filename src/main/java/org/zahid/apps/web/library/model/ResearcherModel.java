package org.zahid.apps.web.library.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearcherModel {
    
    private Long researcherId;
    
    private String researcherName;
    
    private List<BookModel> books;
}
