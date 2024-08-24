package com.alabtaal.library.payload.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVolumeResponse {

  private UUID id;
  private UUID book;
  private String volumeName;
  private String bookName;
  private String rackName;
  private String remarks;
}
