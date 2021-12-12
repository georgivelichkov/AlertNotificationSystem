package com.sap.servicebroker.interfaces;

import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;

public interface IServiceInstanceBindingService {

    CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest r);

    void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest r);

}
