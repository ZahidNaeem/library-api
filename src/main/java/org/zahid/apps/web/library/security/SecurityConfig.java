package org.zahid.apps.web.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zahid.apps.web.library.security.jwt.JwtAuthEntryPoint;
import org.zahid.apps.web.library.security.jwt.JwtAuthTokenFilter;
import org.zahid.apps.web.library.security.jwt.JwtProvider;
import org.zahid.apps.web.library.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String[] AUTH_WHITELIST = {
      "/",
      "/favicon.ico",
      "/**/*.png",
      "/**/*.gif",
      "/**/*.svg",
      "/**/*.jpg",
      "/**/*.html",
      "/**/*.css",
      "/**/*.js",
      "/swagger-resources/**",
      "/swagger-ui.html",
      "/v2/api-docs",
      "/webjars/**",
      "/error"
  };

  @Autowired
  @Qualifier("userDetailsServiceImpl")
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtAuthEntryPoint unauthorizedHandler;

  @Bean
  public JwtAuthTokenFilter authenticationJwtTokenFilter() {
    return JwtAuthTokenFilter.builder().build();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(AUTH_WHITELIST)
        .permitAll()
        .antMatchers("/auth/**")
        .permitAll()
//                .antMatchers("/**")
//                .permitAll()
        .antMatchers("/user/checkUsernameAvailability", "/user/checkEmailAvailability")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/users/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
  }
}
