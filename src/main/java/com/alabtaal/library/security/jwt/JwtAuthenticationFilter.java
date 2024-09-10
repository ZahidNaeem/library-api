package com.alabtaal.library.security.jwt;

import com.alabtaal.library.config.JwtConfig;
import com.alabtaal.library.service.UserDetailsServiceImpl;
import com.alabtaal.library.util.Miscellaneous;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtConfig jwtConfig;
  private final JwtProvider jwtProvider;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    try {
      final String jwt = getJwt(request);
      if (StringUtils.isNoneBlank(jwt) && jwtProvider.validateJwtToken(jwt)) {
        final String username = jwtProvider.getUsernameFromJwt(jwt);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (final Exception e) {
      Miscellaneous.logException(LOG, e);
    }
    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) {
    final String authHeader = request.getHeader(jwtConfig.getHeader());

    if (StringUtils.isNoneBlank(authHeader) && authHeader.startsWith(jwtConfig.getPrefix())) {
      return authHeader.replace(jwtConfig.getPrefix(), "");
    }

    return null;
  }
}
