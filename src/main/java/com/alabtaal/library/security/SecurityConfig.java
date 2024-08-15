package com.alabtaal.library.security;

import com.alabtaal.library.security.jwt.JwtAuthEntryPoint;
import com.alabtaal.library.security.jwt.JwtAuthenticationFilter;
import com.alabtaal.library.service.UserDetailsServiceImpl;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

  private static final Logger LOG = LoggerFactory.getLogger(WebSecurity.class);

  private final UserDetailsServiceImpl userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtAuthEntryPoint jwtAuthEntryPoint;
  private final CustomAccessDeniedHandler accessDeniedHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Value("${app.origins.allowed:http://localhost:8080}")
  private String allowedOrigins;

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
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
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

        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/auth/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/users/**").permitAll()
            .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
            .requestMatchers(HttpMethod.PUT, "/**")
            .hasAnyAuthority(RoleName.ROLE_ADMIN.name())
            .requestMatchers(HttpMethod.DELETE, "/**")
            .hasAnyAuthority(RoleName.ROLE_ADMIN.name())
            .anyRequest().authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    LOG.info("Allowed origins: {}", allowedOrigins);
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
//    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "TRACE", "OPTIONS", "PATCH"));
    configuration.setAllowCredentials(true);
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}
