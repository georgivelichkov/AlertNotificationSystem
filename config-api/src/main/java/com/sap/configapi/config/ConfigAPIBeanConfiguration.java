package com.sap.configapi.config;

import com.sap.model.services.ActionsService;
import com.sap.model.services.ConditionsService;
import com.sap.model.services.SubscriptionsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigAPIBeanConfiguration {

    @Bean
    public ActionsService getActionService() {
        return new ActionsService();
    }

    @Bean
    public ConditionsService getConditionsService() {
        return new ConditionsService();
    }

    @Bean
    public SubscriptionsService getSubscriptionService() {
        return new SubscriptionsService();
    }


}
