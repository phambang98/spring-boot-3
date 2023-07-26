package com.example.spring.rest.api.config;

import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SwaggerIndexTransformer swaggerIndexTransformer;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui*/*swagger-initializer.js")
                .addResourceLocations("classpath:/META-INF/resources/webjars/","/resources/**")
                .setCachePeriod(0).resourceChain(false)
                .addTransformer(this.swaggerIndexTransformer);
        registry.addResourceHandler("/swagger-ui*/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/","/resources/")
                .resourceChain(false)
                .addTransformer(this.swaggerIndexTransformer);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}
