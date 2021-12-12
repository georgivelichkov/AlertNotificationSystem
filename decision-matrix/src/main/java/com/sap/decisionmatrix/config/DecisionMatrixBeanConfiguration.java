package com.sap.decisionmatrix.config;

import com.sap.decisionmatrix.listeners.EventQueueListener;
import com.sap.messagebroker.MessageBrokerService;
import com.sap.model.services.SubscriptionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecisionMatrixBeanConfiguration {

    @Bean
    public EventQueueListener queueListener(){
        return new EventQueueListener(subscriptionsService(), messageBrokerService());
    }

    @Bean
    public SubscriptionsService subscriptionsService() {
        return new SubscriptionsService();
    }

    @Bean
    public MessageBrokerService messageBrokerService() {
        return new MessageBrokerService();
    }

}
