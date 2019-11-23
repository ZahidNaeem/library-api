package org.zahid.apps.web.pos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource("classpath:configprops.properties")
@ConfigurationProperties(prefix = "pos")
public class ConfigProperties {
    private Map<String, String> app;
    private Map<String, String> db;

    public Map<String, String> getApp() {
        return app;
    }

    public void setApp(Map<String, String> app) {
        this.app = app;
    }

    public Map<String, String> getDb() {
        return db;
    }

    public void setDb(Map<String, String> db) {
        this.db = db;
    }
}
