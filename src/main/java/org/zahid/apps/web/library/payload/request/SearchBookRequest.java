package org.zahid.apps.web.library.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookRequest {
    private Integer author;
    private Integer subject;
    private Integer publisher;
    private Integer researcher;
}
