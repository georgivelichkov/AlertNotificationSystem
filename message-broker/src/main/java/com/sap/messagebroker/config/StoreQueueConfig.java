package com.sap.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StoreQueueConfig {

    private final String dbQueue;
    private final String dbRoutingKey;

    public StoreQueueConfig(@Value("${jsa.rabbitmq.db_queue}") String dbQueue,
                            @Value("${jsa.rabbitmq.db_routing_key}") String dbRoutingKey) {
        this.dbQueue = dbQueue;
        this.dbRoutingKey = dbRoutingKey;
    }

    @Bean
    public Queue storeQueue() {
        return new Queue(dbQueue, true,false,false);
    }

    @Bean
    public Binding storeBinding(TopicExchange exchange) {
        return BindingBuilder.bind(storeQueue()).to(exchange).with(dbRoutingKey);
    }

}
