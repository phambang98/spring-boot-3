package com.example.spring.mq.controller;

import com.example.core.utils.ConvertUtils;
import com.example.spring.mq.model.AbstractBaseRequest;
import com.example.spring.mq.model.AbstractBaseResponse;
import com.example.spring.mq.service.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/xml")
public class MqXmlController {
    private static Logger logger = LoggerFactory.getLogger(MqXmlController.class);

    @Qualifier("rabbitTemplateXml")
    @Autowired
    private RabbitTemplate rabbitTemplateXml;

    @Value("${spring-mq.rabbitmq.exchangeXmlSend}")
    private String exchangeXmlSend;

    @Value("${spring-mq.rabbitmq.routingKeyXmlSend}")
    private String routingKeyXmlSend;

    @Value("${spring-mq.rabbitmq.exchangeXmlReply}")
    private String exchangeXmlReply;

    @Value("${spring-mq.rabbitmq.routingKeyXmlReply}")
    private String routingKeyXmlReply;


    @Autowired
    private ApplicationContext context;


    @PostMapping(value = "/send-message")
    public String sendMessage(@RequestBody String input) {

        rabbitTemplateXml.convertAndSend(exchangeXmlSend, routingKeyXmlSend, input);
        logger.info("Send msg = {}", input);
        return "Message sent to the RabbitMQ - spring-mq.exchangeXmlSend - Successfully";
    }

    @PostMapping(value = "/reply-message")
    public String replyMessage(@RequestBody String input) {
        rabbitTemplateXml.convertAndSend(exchangeXmlReply, routingKeyXmlReply, input);
        logger.info("Send msg = {}", input);
        return "Message sent to the RabbitMQ - spring-mq.exchangeXmlReply - Successfully";
    }

    @GetMapping(value = "/receiver/{queueName}")
    public String receiver(@PathVariable("queueName") String input) {
        try {
            Message tmp = rabbitTemplateXml.receive(input);
            logger.info("ResponseInfo: {}", tmp);
            return tmp != null ? new String(tmp.getBody()) : "";
        } catch (Exception ex) {
            logger.error("", ex);
            return "FAIL";
        }
    }

    @RabbitListener(queues = {"spring-mq.queueXmlSend"}, errorHandler = "errorHandlerXml")
    public void receiverXmlSend(Message message, String input, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
        String output;
        try {
            String messageId = message.getMessageProperties().getMessageId();
            logger.info("Receiver with msgId = {}", messageId);
            AbstractBaseRequest request = ConvertUtils.stringXmlToObject(input, AbstractBaseRequest.class);
            AbstractBaseService baseService = (AbstractBaseService) context.getBean(request.getHeader().getMq());
            AbstractBaseResponse response = baseService.process(request);
            output = ConvertUtils.objectToStringXml(response, AbstractBaseRequest.class.getName());

        } catch (Exception e) {
            logger.error("", e);
            output = "INVALID REQUEST";
        }
        rabbitTemplateXml.convertAndSend(exchangeXmlReply, routingKeyXmlReply, output);
    }


}

