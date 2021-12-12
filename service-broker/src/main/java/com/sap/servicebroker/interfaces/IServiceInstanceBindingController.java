package com.sap.servicebroker.interfaces;

import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.http.ResponseEntity;

public interface IServiceInstanceBindingController {

    ResponseEntity<CreateServiceInstanceBindingResponse> createServiceInstanceBinding(CreateServiceInstanceBindingRequest r);

    ResponseEntity<?> deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest r);
}
