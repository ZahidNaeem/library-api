package com.alabtaal.library.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JpaConfig {

  private static final String LIBRARY_USERNAME = System.getenv("LIBRARY_USERNAME");
  private static final String LIBRARY_PASSWORD = System.getenv("LIBRARY_PASSWORD");

  @Value("${spring.datasource.url}")
  private String jdbcUrl;

  @Bean
  public DataSource datasource() {
    return DataSourceBuilder
        .create()
        .driverClassName("oracle.jdbc.OracleDriver")
        .url(jdbcUrl)
        .username(LIBRARY_USERNAME)
        .password(LIBRARY_PASSWORD)
        .build();
  }

}
