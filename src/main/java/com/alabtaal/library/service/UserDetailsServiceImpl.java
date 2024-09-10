package com.alabtaal.library.service;

import com.alabtaal.library.entity.UserEntity;
import com.alabtaal.library.exception.ResourceNotFoundException;
import com.alabtaal.library.model.UserPrincipal;
import com.alabtaal.library.util.Miscellaneous;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

  private final UserServiceImpl userService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String username)
      throws UsernameNotFoundException {

    final UserEntity user;
    try {
      user = userService.findByUsername(username);
    } catch (final ResourceNotFoundException e) {
      Miscellaneous.logException(LOG, e);
      throw new UsernameNotFoundException("User Not Found with -> username : " + username);
    }


    final List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toList());

    return UserPrincipal.builder()
        .id(user.getId())
        .name(user.getName())
        .username(user.getUsername())
        .email(user.getEmail())
        .password(user.getPassword())
        .activationStatus(user.getActivationStatus())
        .authorities(authorities)
        .build();
  }
}
