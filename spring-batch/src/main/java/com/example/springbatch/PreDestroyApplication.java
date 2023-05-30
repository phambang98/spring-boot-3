package com.example.springbatch;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PreDestroyApplication {
    Logger logger = LoggerFactory.getLogger(PreDestroyApplication.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PreDestroy
    public void onDestroy() {
        logger.warn("Spring Boot 3 is destroyed!");
    }

}
