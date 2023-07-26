package com.example.spring.web.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.example.core.repository")
@EntityScan("com.example.core.entity")
public class SpringWebServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebServicesApplication.class, args);
    }

}
