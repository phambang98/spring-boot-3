package com.example.spring.mq.error;


import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HandleExceptionMq {

    @Bean
    public RabbitListenerErrorHandler errorHandlerXml() {
        return (m1, m2, e) -> {
            System.out.println(m1);
            System.out.println(m2);
            System.out.println(e);
            return null;
        };
    }
}
