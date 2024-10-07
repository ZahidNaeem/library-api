package com.alabtaal.library.security;

import com.alabtaal.library.enumeration.RoleName;
import com.alabtaal.library.security.jwt.JwtAuthEntryPoint;
import com.alabtaal.library.security.jwt.JwtAuthenticationFilter;
import com.alabtaal.library.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final Logger LOG = LoggerFactory.getLogger(SecurityConfig.class);
  private static final String[] AUTH_WHITELIST = {
      "/",
      "/favicon.ico",
      "/*/*.png",
      "/*/*.gif",
      "/*/*.svg",
      "/*/*.jpg",
      "/*/*.html",
      "/*/*.css",
      "/*/*.js",
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/v2/api-docs",
      "/webjars/**",
      "/error"
  };

  public static List<String> getOpenApiEndpoints() {
    final List<String> openApiEndpoints = new ArrayList<>();
    openApiEndpoints.add("/auth/**");
    openApiEndpoints.add("/users/**::" + HttpMethod.POST);
    openApiEndpoints.add("/users/**::" + HttpMethod.PUT);
    openApiEndpoints.add("/user/me::" + HttpMethod.GET);
    openApiEndpoints.add("/**/refresh::" + HttpMethod.GET);
    return openApiEndpoints;
  }

  private String[] getAPIsWithoutMethod() {
    List<String> openApiEndpoints = getOpenApiEndpoints();
    return openApiEndpoints
        .stream()
        .filter(StringUtils::isNoneBlank)
        .filter(element -> element.split("::").length == 1)
        .toList()
        .toArray(new String[0]);
  }

  private Map<HttpMethod, String[]> getAPIsWithMethod() {
    List<String> openApiEndpoints = getOpenApiEndpoints();
    return openApiEndpoints
        .stream()
        .filter(StringUtils::isNotBlank)
        .map(element -> element.split("::"))
        .filter(element -> element.length == 2)
        .collect(Collectors.groupingBy(path -> HttpMethod.valueOf(path[1]),
            Collectors.mapping(path -> path[0], Collectors.toSet())))
        .entrySet().stream()
        .collect(Collectors.toMap(Entry::getKey,
            entry -> entry.getValue().toArray(new String[0])));
  }

  private final UserDetailsServiceImpl userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  @Value("${app.origins.allowed:http://localhost:3000}")
  private String allowedOrigins;

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    final AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            exceptionHandling -> {
              exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint);
              exceptionHandling.accessDeniedHandler(accessDeniedHandler);
            })

        .authorizeHttpRequests(auth -> {
          getAPIsWithMethod()
              .forEach((method, paths) -> auth
                  .requestMatchers(method, paths)
                  .permitAll());
          auth
              .requestMatchers(AUTH_WHITELIST)
              .permitAll()
              .requestMatchers(getAPIsWithoutMethod())
              .permitAll()
              .requestMatchers(HttpMethod.PUT, "/**")
              .hasAnyAuthority(RoleName.ROLE_ADMIN.name())
              .requestMatchers(HttpMethod.DELETE, "/**")
              .hasAnyAuthority(RoleName.ROLE_ADMIN.name())
              .anyRequest().authenticated();
        })
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    LOG.info("Allowed origins: {}", allowedOrigins);
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "TRACE", "OPTIONS", "PATCH"));
    configuration.setAllowCredentials(true);
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  public static boolean isOpen(HttpServletRequest request) {
    return getOpenApiEndpoints()
        .stream()
        .anyMatch(
            endpoint -> {
              final String[] path = endpoint.split("::");
              String uriPath = "";
              try {
                URI uri = new URI(request.getRequestURI());
                uriPath = uri.getPath();
              } catch (URISyntaxException e) {
                throw new RuntimeException(e);
              }
              final boolean matches = uriPath.matches("/api" + normalize(path[0]))
                  && (path.length == 1 || HttpMethod.valueOf(path[1]).equals(HttpMethod.valueOf(request.getMethod())));
              LOG.debug("Path: {} - Method: {} - Result: {}", uriPath, request.getMethod(), matches);
              return matches;
            });
  }

  public static String normalize(final String uri) {
    return uri.replaceAll("\\*\\*", ".*");
  }

}
