package org.zahid.apps.web.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zahid.apps.web.library.interceptor.CustomHandlerInterceptorAdapter;

//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Bean
//    public CustomHandlerInterceptorAdapter customHandlerInterceptorAdapter() {
//        return new CustomHandlerInterceptorAdapter();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(customHandlerInterceptorAdapter());
//    }
}
