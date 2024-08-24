package com.alabtaal.library.model;

import com.alabtaal.library.enumeration.ActivationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserPrincipal implements UserDetails {

  /**
   *
   */


  private UUID id;

  private String name;

  private String username;

  private String email;

  @JsonIgnore
  private String password;

  private ActivationStatus activationStatus;

  private String token;

  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return activationStatus == ActivationStatus.ACTIVE;
  }
}
