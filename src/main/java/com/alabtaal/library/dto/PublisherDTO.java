package com.alabtaal.library.dto;

import com.alabtaal.library.model.PublisherModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDTO {

  private NavigationDtl navigationDtl;
  private PublisherModel publisher;
}
