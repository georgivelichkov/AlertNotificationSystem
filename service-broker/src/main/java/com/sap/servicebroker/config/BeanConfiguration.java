package com.sap.servicebroker.config;

import com.sap.credentialsprovider.oauth.OAuth2CredentialsProvider;
import com.sap.servicebroker.dto.XsuaaIdentityProvider;
import com.sap.servicebroker.service.ServiceBindingDAOService;
import com.sap.servicebroker.service.ServiceBindingService;
import com.sap.servicebroker.service.ServiceInstance;
import com.sap.servicebroker.service.ServiceInstanceDAOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    private final Environment env;

    @Autowired
    public BeanConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public XsuaaIdentityProvider xsuaaIdentityProvider() {
        return new XsuaaIdentityProvider(restTemplate(), credentialsProvider(), env.getProperty("xsuaa.url"), env.getProperty("xsuaa.xsappname"));
    }

    @Bean
    public OAuth2CredentialsProvider credentialsProvider() {
        return new OAuth2CredentialsProvider(env.getProperty("xsuaa.clientId"),
                                             env.getProperty("xsuaa.clientsecret"),
                                             env.getProperty("xsuaa.url"),
                                             restTemplate());
    }

    @Bean
    public ServiceInstanceDAOService daoService() {
        return new ServiceInstanceDAOService();
    }

    @Bean
    public ServiceBindingDAOService bindingDAOService() {
        return new ServiceBindingDAOService();
    }

    @Bean
    public ServiceInstance serviceInstanceService() {
        return new ServiceInstance(daoService());
    }

    @Bean
    public ServiceBindingService serviceBindingService() {
        return new ServiceBindingService( bindingDAOService(), daoService(), xsuaaIdentityProvider());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Catalog catalog() {
        Plan plan = Plan.builder()
                .id("425141a0-85d8-11e9-bc42-526af7764f64")
                .name("standard")
                .description("A simple MiniANS plan")
                .free(true)
                .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .id("4a97689e-85d8-11e9-bc42-526af7764f64")
                .name("minians-service-broker")
                .description("A minified version of ANS")
                .bindable(true)
                .tags("mini-ans")
                .plans(plan)
                .metadata("displayName", "minians-service-broker")
                .metadata("longDescription", "A minified version of ANS service")
                .metadata("providerDisplayName", "SAP MiniANS")
                .build();

        return Catalog.builder()
                .serviceDefinitions(serviceDefinition)
                .build();
    }

}
