package com.sap.eventapi.config;

import com.sap.messagebroker.MessageBrokerService;
import com.sap.model.services.EventsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventAPIBeanConfiguration {

    @Bean
    public EventsService getEventService() {
        return new EventsService();
    }

    @Bean
    public MessageBrokerService getMessageBroker() {
        return new MessageBrokerService();
    }

}
