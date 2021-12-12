package com.sap.servicebroker.interfaces;

import org.springframework.cloud.servicebroker.model.instance.*;

public interface IServiceInstanceService {
    CreateServiceInstanceResponse createServiceInstance( CreateServiceInstanceRequest request );

    UpdateServiceInstanceResponse updateServiceInstance( UpdateServiceInstanceRequest request );

    DeleteServiceInstanceResponse deleteServiceInstance( DeleteServiceInstanceRequest request );

    GetLastServiceOperationResponse getLastOperation( GetLastServiceOperationRequest request );
}