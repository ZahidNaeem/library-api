package org.zahid.apps.web.library.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVolumeResponse {
    private Long volumeId;
    private Long bookId;
    private String volumeName;
    private String bookName;
    private String rackName;
    private String remarks;
}
