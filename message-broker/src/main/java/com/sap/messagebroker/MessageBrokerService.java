package com.sap.messagebroker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.model.dto.EventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


public class MessageBrokerService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageBrokerService.class);
    private AmqpTemplate amqpTemplate;
    private JavaMailSender emailSender;
    private String exchange;
    private String slackUrl;

    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    @Value("${jsa.rabbitmq.exchange}")
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Autowired
    public void setSlackUrl(@Value("${jsa.rabbitmq.exchange}") String slackUrl) {
        this.slackUrl = slackUrl;
    }

    @Autowired
    public void setEmailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEventToQueue(EventDto msg, QueueType queueType) throws JsonProcessingException {
        String message = "Sending events to ".concat(queueType.getType()).concat("queue!!!");
        LOG.info(message);
        ObjectMapper objectMapper = new ObjectMapper();
        String msgStr = objectMapper.writeValueAsString(msg);
        amqpTemplate.convertAndSend(exchange, queueType.getRoutingKey(), msgStr);
    }

    public void sendSimpleMessage(String to, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("ANS EVENT!!!");
            message.setText(text);

            emailSender.send(message);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    public String sendMessageToSlack(String message) {
        Map<String, String> params = new HashMap<>();
        params.put("text", message);

        RestTemplate restTemplate = new RestTemplate();
        // Add the Jackson message converter
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.postForObject(slackUrl, params, String.class);
    }


}
