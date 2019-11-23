package org.zahid.apps.web.pos.security.jwt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOG = LogManager.getLogger(JwtAuthEntryPoint.class);

  @Override
  public void commence(HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException e)
      throws IOException, ServletException {

    LOG.error("Unauthorized error. Message - {}", e.getMessage());
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
  }
}
