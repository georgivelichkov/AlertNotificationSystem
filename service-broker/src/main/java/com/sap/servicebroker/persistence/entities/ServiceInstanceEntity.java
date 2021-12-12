package com.sap.servicebroker.persistence.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.UpdateServiceInstanceRequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ServiceInstanceEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "service-instance-uuid")
    @GenericGenerator(name = "service-instance-uuid", strategy = "uuid")
    private String id;

    @Column(name = "service_definition_id")
    private String serviceDefinitionId;

    @Column(name = "plan_id")
    private String planId;

    @Column(name = "organization_guid")
    private String organizationGuid;

    @Column(name = "space_guid")
    private String spaceGuid;

    @Column(name = "client_id")
    private String clientId;

    public ServiceInstanceEntity() {
        // FOR JPA
    }

    public ServiceInstanceEntity(CreateServiceInstanceRequest request) {
        this.serviceDefinitionId = request.getServiceDefinitionId();
        this.planId = request.getPlanId();
        this.organizationGuid = request.getOrganizationGuid();
        this.spaceGuid = request.getSpaceGuid();
        this.id = request.getServiceInstanceId();
    }

    public ServiceInstanceEntity(DeleteServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
        this.serviceDefinitionId = request.getServiceDefinitionId();
    }

    public ServiceInstanceEntity(UpdateServiceInstanceRequest request) {
        this.id = request.getServiceInstanceId();
        this.planId = request.getPlanId();
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public String getPlanId() {
        return planId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServiceDefinitionId() {
        return serviceDefinitionId;
    }
}
