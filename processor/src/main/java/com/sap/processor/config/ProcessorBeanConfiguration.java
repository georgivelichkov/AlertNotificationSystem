package com.sap.processor.config;

import com.sap.messagebroker.MessageBrokerService;
import com.sap.model.services.EventsService;
import com.sap.model.services.SubscriptionsService;
import com.sap.processor.listeners.EmailQueueListener;
import com.sap.processor.listeners.SlackQueueListener;
import com.sap.processor.listeners.StoreQueueListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessorBeanConfiguration {

    @Bean
    public SubscriptionsService subscriptionsService() {
        return new SubscriptionsService();
    }


    @Bean
    public EmailQueueListener emailQueueListener() {
        return new EmailQueueListener();
    }

    @Bean
    public SlackQueueListener slackQueueListener() {
        return new SlackQueueListener();
    }

    @Bean
    public StoreQueueListener storeQueueListener() {
        return new StoreQueueListener();
    }

    @Bean
    public MessageBrokerService messageBrokerService() { return new MessageBrokerService(); }

    @Bean
    public EventsService eventsService() { return new EventsService(); }

}
