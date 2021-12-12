package com.sap.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackQueueConfig {

    private final String slackQueue;
    private final String slackRoutingKey;

    public SlackQueueConfig( @Value("${jsa.rabbitmq.slack_queue}") String slackQueue, //
                             @Value("${jsa.rabbitmq.slack_routing_key}") String slackRoutingKey
                           ) {

        this.slackQueue = slackQueue;
        this.slackRoutingKey = slackRoutingKey;
    }

    @Bean
    public Queue slackQueue() {
        return new Queue(slackQueue, true,false,false);
    }

    @Bean
    public Binding slackBinding(TopicExchange exchange) {
        return BindingBuilder.bind(slackQueue()).to(exchange).with(slackRoutingKey);
    }



}
