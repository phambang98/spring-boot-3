package com.example.spring.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

    @Value("${spring-mq.rabbitmq.queueJsonSend}")
    String queueJsonSend;

    @Value("${spring-mq.rabbitmq.exchangeJsonSend}")
    String exchangeJsonSend;

    @Value("${spring-mq.rabbitmq.routingKeyJsonSend}")
    private String routingKeyJsonSend;

    @Value("${spring-mq.rabbitmq.queueJsonReply}")
    String queueJsonReply;

    @Value("${spring-mq.rabbitmq.exchangeJsonReply}")
    String exchangeJsonReply;

    @Value("${spring-mq.rabbitmq.routingKeyJsonReply}")
    private String routingKeyJsonReply;

    @Autowired
    private LocalValidatorFactoryBean validator;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setValidator(this.validator);
    }

    @Bean
    public Queue queueJsonSend() {
        return new Queue(queueJsonSend, false);
    }

    @Bean
    public DirectExchange exchangeJsonSend() {
        return new DirectExchange(exchangeJsonSend);
    }

    @Bean
    public Binding bindingJsonSend(Queue queueJsonSend, DirectExchange exchangeJsonSend) {
        return BindingBuilder.bind(queueJsonSend).to(exchangeJsonSend).with(routingKeyJsonSend);
    }

    @Bean
    public Queue queueJsonReply() {
        return new Queue(queueJsonReply, false);
    }

    @Bean
    public DirectExchange exchangeJsonReply() {
        return new DirectExchange(exchangeJsonReply);
    }

    @Bean
    public Binding bindingJsonReply(Queue queueJsonReply, DirectExchange exchangeJsonReply) {
        return BindingBuilder.bind(queueJsonReply).to(exchangeJsonReply).with(routingKeyJsonReply);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplateJson() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Value("${spring-mq.rabbitmq.queueXmlSend}")
    String queueXmlSend;

    @Value("${spring-mq.rabbitmq.exchangeXmlSend}")
    String exchangeXmlSend;

    @Value("${spring-mq.rabbitmq.routingKeyXmlSend}")
    private String routingKeyXmlSend;

    @Value("${spring-mq.rabbitmq.queueXmlReply}")
    String queueXmlReply;

    @Value("${spring-mq.rabbitmq.exchangeXmlReply}")
    String exchangeXmlReply;

    @Value("${spring-mq.rabbitmq.routingKeyXmlReply}")
    private String routingKeyXmlReply;

    @Bean
    public Queue queueXmlSend() {
        return new Queue(queueXmlSend, false);
    }

    @Bean
    public DirectExchange exchangeXmlSend() {
        return new DirectExchange(exchangeXmlSend);
    }

    @Bean
    public Binding bindingXmlSend(Queue queueXmlSend, DirectExchange exchangeXmlSend) {
        return BindingBuilder.bind(queueXmlSend).to(exchangeXmlSend).with(routingKeyXmlSend);
    }

    @Bean
    public Queue queueXmlReply() {
        return new Queue(queueXmlReply, false);
    }

    @Bean
    public DirectExchange exchangeXmlReply() {
        return new DirectExchange(exchangeXmlReply);
    }

    @Bean
    public Binding bindingXmlReply(Queue queueXmlReply, DirectExchange exchangeXmlReply) {
        return BindingBuilder.bind(queueXmlReply).to(exchangeXmlReply).with(routingKeyXmlReply);
    }

    @Bean
    public RabbitTemplate rabbitTemplateXml() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public MessageConverter messageConverter() {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        converter.setCreateMessageIds(true);
        return converter;
    }

}