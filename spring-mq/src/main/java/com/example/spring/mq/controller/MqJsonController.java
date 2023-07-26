package com.example.spring.mq.controller;

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
@RequestMapping(value = "/json")
public class MqJsonController {
    private static Logger logger = LoggerFactory.getLogger(MqJsonController.class);

    @Qualifier("rabbitTemplateJson")
    @Autowired
    private RabbitTemplate rabbitTemplateJson;

    @Value("${spring-mq.rabbitmq.exchangeJsonSend}")
    private String exchangeJsonSend;

    @Value("${spring-mq.rabbitmq.routingKeyJsonSend}")
    private String routingKeyJsonSend;

    @Value("${spring-mq.rabbitmq.exchangeJsonReply}")
    private String exchangeJsonReply;

    @Value("${spring-mq.rabbitmq.routingKeyJsonReply}")
    private String routingKeyJsonReply;


    @Autowired
    private ApplicationContext context;


    @PostMapping(value = "/send-message")
    public String sendMessage(@RequestBody String baseBean) {
        rabbitTemplateJson.convertAndSend(exchangeJsonSend, routingKeyJsonSend, baseBean);
        logger.info("Send msg = {}", baseBean);
        return "Message sent to the RabbitMQ - spring-mq.exchangeJsonSend - Successfully";
    }

    @PostMapping(value = "/reply-message")
    public String replyMessage(@RequestBody String baseBean) {
        rabbitTemplateJson.convertAndSend(exchangeJsonReply, routingKeyJsonReply, baseBean);
        logger.info("Send msg = {}", baseBean);
        return "Message sent to the RabbitMQ - spring-mq.exchangeJsonReply - Successfully";
    }

    @RequestMapping(value = "/receiver/{queueName}", method = RequestMethod.GET)
    public String receiver(@PathVariable("queueName") String queueName) {
        try {
            Message tmp = rabbitTemplateJson.receive(queueName);
            logger.info("ResponseInfo: {}", tmp);
            if (tmp == null) {
                return "FAIL";
            }
            return new String(tmp.getBody());
        } catch (Exception ex) {
            logger.error("", ex);
            return "FAIL";
        }
    }

    @RabbitListener(queues = {"spring-mq.queueJsonSend"})
    public void receiverJsonSend(Message message, AbstractBaseRequest baseBean, @Header(AmqpHeaders.CONSUMER_QUEUE) String queue) {
        try {
            String messageId = message.getMessageProperties().getMessageId();
            logger.info("Receiver with msgId = {}", messageId);
            AbstractBaseService baseService = (AbstractBaseService) context.getBean(baseBean.getHeader().getMq());
            AbstractBaseResponse output = baseService.process(baseBean);

            rabbitTemplateJson.convertAndSend(exchangeJsonReply, routingKeyJsonReply, output);
        } catch (Exception e) {
            logger.error("", e);
        }
    }


}

