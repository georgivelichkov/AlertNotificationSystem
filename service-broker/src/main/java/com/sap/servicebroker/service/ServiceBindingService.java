package com.sap.servicebroker.service;

import com.sap.servicebroker.dto.OAuth2Credentials;
import com.sap.servicebroker.dto.XsuaaIdentityProvider;
import com.sap.servicebroker.exceptions.OAuthIdentityProviderServiceException;
import com.sap.servicebroker.persistence.entities.ServiceBindingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingExistsException;
import org.springframework.cloud.servicebroker.model.binding.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ServiceBindingService implements ServiceInstanceBindingService {

    private final ServiceInstanceDAOService daoService;
    private final ServiceBindingDAOService bindingDAOService;
    private final XsuaaIdentityProvider identityProvider;

    @Autowired
    public  ServiceBindingService(ServiceBindingDAOService bindingDAOService, //
                                 ServiceInstanceDAOService daoService, //
                                 XsuaaIdentityProvider identityProvider
                                 ) {

        this.bindingDAOService = bindingDAOService;
        this.daoService = daoService;
        this.identityProvider = identityProvider;
    }

    @Override
    public Mono<CreateServiceInstanceBindingResponse> createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
        String serviceInstanceId = request.getServiceInstanceId();
        String bindingId = request.getBindingId();

        if (daoService.existsById(bindingId)) {
            throw new ServiceInstanceBindingExistsException(serviceInstanceId, bindingId);
        }

        //
        // create credentials and store for later retrieval
        //
        try {
            OAuth2Credentials credentials = identityProvider.get(serviceInstanceId);

            CreateServiceInstanceBindingResponse response = CreateServiceInstanceAppBindingResponse.builder()
                    .credentials("url", credentials.getOAuthServiceUrl())
                    .credentials("username", credentials.getClientId())
                    .credentials("password", credentials.getClientSecret())
                    .bindingExisted(false)
                    .async(true)
                    .build();

            ServiceBindingEntity bindingEntity = new ServiceBindingEntity(bindingId, serviceInstanceId, request.getAppGuid());
            bindingDAOService.save(bindingEntity);

            return Mono.just(response);
        } catch (OAuthIdentityProviderServiceException e) {
            e.printStackTrace();
        }


        return Mono.just(null);
    }

    @Override
    public Mono<DeleteServiceInstanceBindingResponse> deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
        String serviceInstanceId = request.getServiceInstanceId();
        String bindingId = request.getBindingId();

        //
        // delete any binding-specific credentials
        //

        //identityProvider.delete(serviceInstanceId);

        ServiceBindingEntity binding = bindingDAOService.get(bindingId);
        bindingDAOService.delete(binding);

        return Mono.just(DeleteServiceInstanceBindingResponse.builder()
                .async(true)
                .build());
    }

    @Override
    public Mono<GetServiceInstanceBindingResponse> getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        String serviceInstanceId = request.getServiceInstanceId();
        String bindingId = request.getBindingId();

        //
        // retrieve the details of the specified service binding
        //

        if (!daoService.existsById(bindingId)) {
            throw new ServiceInstanceBindingDoesNotExistException(bindingId);
        }


        OAuth2Credentials credentials = null;

        try {
            credentials = identityProvider.get(serviceInstanceId);
        } catch (OAuthIdentityProviderServiceException e) {
            e.printStackTrace();
        }

        GetServiceInstanceBindingResponse response = GetServiceInstanceAppBindingResponse.builder()
                .credentials("username", credentials.getClientId())
                .credentials("password", credentials.getClientSecret())
                .credentials("url", credentials.getOAuthServiceUrl())
                .build();

        return Mono.just(response);
    }
}
