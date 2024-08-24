package com.alabtaal.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan({"com.alabtaal"})
public class LibraryApplication {

  private static final Logger LOG = LoggerFactory.getLogger(LibraryApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
    LOG.info("=========================================Info =========================================");
    LOG.debug("=========================================Debug=========================================");
  }
}
