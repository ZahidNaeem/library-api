package org.zahid.apps.web.pos.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zahid.apps.web.pos.entity.User;
import org.zahid.apps.web.pos.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepo userRepo;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String usernameOrEmail)
      throws UsernameNotFoundException {

    final User user = userRepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() ->
            new UsernameNotFoundException("User Not Found with -> username or email : " + usernameOrEmail)
        );

    return UserPrincipal.build(user);
  }
}
