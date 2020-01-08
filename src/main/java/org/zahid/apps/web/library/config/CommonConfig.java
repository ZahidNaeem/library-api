package org.zahid.apps.web.library.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zahid.apps.web.library.model.GmailCredentials;
import org.zahid.apps.web.library.service.GmailService;
import org.zahid.apps.web.library.service.impl.AuditorAwareImpl;
import org.zahid.apps.web.library.service.impl.GmailServiceImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CommonConfig {

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
//        final String origin = "http://" + configProperties.getApp().get("server") + ":" + configProperties.getApp().get("port");
        final String origin = "*";
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**").allowedOrigins(origin)
                        .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }

    @Bean
    public GmailService gmailService() throws GeneralSecurityException, IOException {

        final HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        final GmailCredentials gmailCredentials = GmailCredentials.builder()
                .userEmail("hzahidnaeem@gmail.com")
                .clientId("514297343695-ah8j4km4489k6h3vekgkoe5aoqsk8a6d.apps.googleusercontent.com")
                .accessToken("ya29.Il-vB6pG0tXO4pC2HfQSaUY5j247N6cEn71hv0UNSb2zbw0iet79PdoGgg1ZL0SDG-L9xHJD5yYbC4buy1DXIPxxdRse0G6E8VpJu7IjvV3lwsKixkwc49lVMVZ6qOpxqw")
                .refreshToken("1//03bHWuOESp3t4CgYIARAAGAMSNwF-L9Ir6PAXIBl5NlPD2WANT57KJwumAiLwxj65-JUML0AVdn2bicPN7u0PJMNjTCf3P6r-UAc")
                .clientSecret("r27OzcX97imdKNTeOPOIn5tp")
                .build();

        return new GmailServiceImpl(httpTransport, gmailCredentials);
    }

    @Bean
    public AuditorAware<Long> auditorAware(){
        return new AuditorAwareImpl();
    }

    /*public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
            }
        };
    }*/

 /*   public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
            "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        configuration.setExposedHeaders(ImmutableList.of("X-Auth-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
