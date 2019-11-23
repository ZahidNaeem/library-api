package org.zahid.apps.web.pos.security.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank
  private String usernameOrEmail;

  @NotBlank
  private String password;
}
