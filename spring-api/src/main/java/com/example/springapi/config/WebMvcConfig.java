package com.example.springapi.config;

import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private SwaggerIndexTransformer swaggerIndexTransformer;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui*/*swagger-initializer.js")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCachePeriod(0).resourceChain(false)
                .addTransformer(this.swaggerIndexTransformer);
        registry.addResourceHandler("/swagger-ui*/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false)
                .addTransformer(this.swaggerIndexTransformer);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}