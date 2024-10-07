package com.alabtaal.library.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOG = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

  @Override
  public void commence(final HttpServletRequest request,
      final HttpServletResponse response,
      final AuthenticationException e)
      throws IOException {
    final String errorMessage =
        e.getMessage() != null && !e.getMessage().isEmpty() ? e.getMessage()
            : "Error -> Unauthorized";
    LOG.error("Unauthorized access. Message - {}", e.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
  }
}
