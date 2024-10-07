package com.alabtaal.library.security.jwt;

import com.alabtaal.library.exception.BadRequestException;
import com.alabtaal.library.security.SecurityConfig;
import com.alabtaal.library.service.TokenValidationService;
import com.alabtaal.library.service.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Value("${abt.app.accessCookieName:access_token}")
  private String accessCookieName;
  @Value("${abt.app.refreshCookieName:refresh_token}")
  private String refreshCookieName;
  @Value("${abt.app.accessTokenExpiration:#{5*60*1000}}")
  private int accessTokenExpiration;

  private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtProvider jwtProvider;
  private final UserDetailsServiceImpl userDetailsService;
  private final TokenValidationService tokenValidationService;

  String accessToken = null;
  String refreshToken = null;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    accessToken = getToken(request, accessCookieName);
    refreshToken = getToken(request, refreshCookieName);

    try {
      if (validateRefreshToken(refreshToken) && validateAccessToken(accessToken, refreshToken) && usernameMatches(accessToken, refreshToken)) {
        setAuthentication(request, accessToken);
        filterChain.doFilter(request, response);
      } else if (SecurityConfig.isOpen(request)) {
        filterChain.doFilter(request, response);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
      }
    } catch (BadRequestException e) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }
  }

  private boolean validateRefreshToken(String refreshToken) {
    try {
      if (!jwtProvider.validateJwtToken(refreshToken) || tokenValidationService.isTokenDisabled(refreshToken)) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private boolean validateAccessToken(String token, String refreshToken) throws BadRequestException {
    try {
      if (!jwtProvider.validateJwtToken(token) || tokenValidationService.isTokenDisabled(token)) {
        return false;
      }
    } catch (ExpiredJwtException e) {
      final String username = jwtProvider.getUsernameFromJwt(refreshToken);
      accessToken = jwtProvider.generateJwt(username, accessTokenExpiration);
      tokenValidationService.add(username, accessToken);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  private void setAuthentication(HttpServletRequest request, String token) {
    final String username = jwtProvider.getUsernameFromJwt(token);
    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
        userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getToken(HttpServletRequest request, String cookieName) {
    final Cookie token = WebUtils.getCookie(request, cookieName);
    return token != null ? token.getValue() : null;
  }

  private boolean usernameMatches(String accessToken, String refreshToken) {
    final String refreshTokenUsername = Optional.ofNullable(jwtProvider.getUsernameFromJwt(refreshToken)).orElse("");
    final String accessTokenUsername = Optional.ofNullable(jwtProvider.getUsernameFromJwt(accessToken)).orElse("");
    return refreshTokenUsername.equals(accessTokenUsername);
  }
}
