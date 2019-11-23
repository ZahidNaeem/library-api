package org.zahid.apps.web.pos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class PointOfSaleApplication {

  private static final Logger LOG = LogManager.getLogger(PointOfSaleApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(PointOfSaleApplication.class, args);
    LOG.debug("==============================================Debug===========================================");
    LOG.info("==============================================info============================================");
  }

}
