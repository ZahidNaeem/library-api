package com.alabtaal.library.model;

import com.alabtaal.library.entity.Auditable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends Auditable<String> {

  private UUID id;
  private String name;
  private String username;
  private String email;
  private String password;
  private Set<String> roles = new HashSet<>();
}
