package com.alabtaal.library.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {

  private UUID id;
  private String username;
  private String name;
  private Set<String> roles = new HashSet<>();
}
