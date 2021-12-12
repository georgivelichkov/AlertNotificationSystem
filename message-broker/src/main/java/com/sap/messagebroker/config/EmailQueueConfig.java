package com.sap.messagebroker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailQueueConfig {

    private static final Logger LOG = LoggerFactory.getLogger(EmailQueueConfig.class);
    private final String emailQueue;
    private final String emailRoutingKey;

    public EmailQueueConfig( @Value("${jsa.rabbitmq.email_queue}") String emailQueue, //
                             @Value("${jsa.rabbitmq.email_routing_key}") String emailRoutingKey //
                           ) {

        this.emailQueue = emailQueue;
        this.emailRoutingKey = emailRoutingKey;
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true,false,false);
    }

    @Bean
    public Binding emailBinding(TopicExchange exchange) {
        return BindingBuilder.bind(emailQueue()).to(exchange).with(emailRoutingKey);
    }

}
