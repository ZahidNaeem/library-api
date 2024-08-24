package com.alabtaal.library.payload.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBookRequest {

  private UUID author;
  private UUID subject;
  private UUID publisher;
  private UUID researcher;
}
