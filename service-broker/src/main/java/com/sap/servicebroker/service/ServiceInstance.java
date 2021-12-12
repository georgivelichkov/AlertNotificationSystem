package com.sap.servicebroker.service;

import com.sap.servicebroker.persistence.entities.ServiceInstanceEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import reactor.core.publisher.Mono;

public class ServiceInstance implements ServiceInstanceService {

    private static final Log LOG = LogFactory.getLog(ServiceInstance.class);

    private final ServiceInstanceDAOService daoService;

    @Autowired
    public ServiceInstance(ServiceInstanceDAOService daoService) {
        this.daoService = daoService;

    }

    @Override
    public Mono<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request) {

        if (!daoService.existsById(request.getServiceInstanceId())) {
            ServiceInstanceEntity si = new ServiceInstanceEntity(request);
            daoService.save(si);
        }
        else {
            throw new ServiceBrokerException("Could not create serviceInstance " + request.getServiceInstanceId());
        }

        return Mono.just(CreateServiceInstanceResponse.builder().build());
    }

    @Override
    public Mono<UpdateServiceInstanceResponse> updateServiceInstance(UpdateServiceInstanceRequest request) {
        String sid = request.getServiceInstanceId();

        ServiceInstanceEntity instance = daoService.get(sid);



        daoService.delete(instance);
        daoService.save(new ServiceInstanceEntity(request));

        return Mono.just(UpdateServiceInstanceResponse.builder().build());
    }

    @Override
    public Mono<DeleteServiceInstanceResponse> deleteServiceInstance(DeleteServiceInstanceRequest request) {
        String sid = request.getServiceInstanceId();

        ServiceInstanceEntity serviceInstanceEntity = daoService.get(sid);

        daoService.delete(serviceInstanceEntity);

        return Mono.just(DeleteServiceInstanceResponse.builder().build());
    }

    @Override
    public Mono<GetLastServiceOperationResponse> getLastOperation(GetLastServiceOperationRequest request) {
        return Mono.just(GetLastServiceOperationResponse.builder()
                .operationState(OperationState.SUCCEEDED)
                .build());
    }
}
