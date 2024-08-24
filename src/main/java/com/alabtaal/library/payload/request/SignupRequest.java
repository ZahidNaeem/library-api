package com.alabtaal.library.payload.request;

import com.alabtaal.library.enumeration.ActivationStatus;
import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.marker.UserInterface;
import com.alabtaal.library.validation.ExtendedEmailValidator;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest implements UserInterface {

  @NotBlank(message = "First name must not be blank")
  @Size(min = 1, max = 50, message = "Name should has min. {min} and max. {max} characters")
  private String name;

  @NotBlank(message = "Username must not be blank")
  @Size(min = 3, max = 50, message = "Username should has min. {min} and max. {max} characters")
  private String username;

  @NotBlank(message = "Email must not be blank")
  @ExtendedEmailValidator
  private String email;

  @NotBlank(message = "Password must not be blank")
  @Size(min = 6, max = 100, message = "Password should has min. {min} and max. {max} characters")
  private String password;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private ActivationStatus activationStatus = ActivationStatus.PENDING;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private Set<RoleName> roles = Set.of(RoleName.ROLE_USER);
}