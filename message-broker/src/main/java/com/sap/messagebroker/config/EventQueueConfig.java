package com.sap.messagebroker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueConfig {

    private final String eventQueue;
    private final String eventRoutingKey;

    public EventQueueConfig(@Value("${jsa.rabbitmq.event_queue}") String eventQueue, //
                            @Value("${jsa.rabbitmq.event_routing_key}") String eventRoutingKey
    ) {

        this.eventQueue = eventQueue;
        this.eventRoutingKey = eventRoutingKey;
    }

    @Bean
    public Queue eventQueue() {
        return new Queue(eventQueue, true,false,false);
    }

    @Bean
    public Binding eventBinding(TopicExchange exchange) {
        return BindingBuilder.bind(eventQueue()).to(exchange).with(eventRoutingKey);
    }

}
