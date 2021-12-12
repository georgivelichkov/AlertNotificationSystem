package com.sap.servicebroker.interfaces;

import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.http.ResponseEntity;

public interface IServiceInstanceController {
    ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(CreateServiceInstanceRequest request);

    ResponseEntity<UpdateServiceInstanceResponse> updateServiceInstance(UpdateServiceInstanceRequest request);

    ResponseEntity<DeleteServiceInstanceResponse> deleteServiceInstance(DeleteServiceInstanceRequest request);

    ResponseEntity<GetLastServiceOperationResponse> getLastOperation(GetLastServiceOperationRequest request);
}