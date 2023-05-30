package com.example.springwebservices.config;

import com.example.springwebservices.endpoint.WebServices;
import com.example.springwebservices.error.ValidationExceptionMapper;
import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;


@Configuration
public class CxfConfiguration {

    @Autowired
    private WebServices webServices;

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<>(new CXFServlet(), "/soap-api/*");
    }

    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "";
    }

    @Bean
    public Server jaxRsServer(Bus bus) {
        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setServiceBeans(Arrays.asList(webServices));
        factory.setBus(bus);
        factory.setAddress("/server");
        factory.setInInterceptors(Arrays.asList(new JAXRSBeanValidationInInterceptor()));
        factory.setOutInterceptors(Arrays.asList(new JAXRSBeanValidationOutInterceptor()));
        factory.setProviders(Arrays.asList(new ValidationExceptionMapper(), new JacksonJsonProvider()));
        return factory.create();
    }


}
