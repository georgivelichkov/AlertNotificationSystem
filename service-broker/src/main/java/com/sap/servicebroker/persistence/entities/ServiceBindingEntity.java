package com.sap.servicebroker.persistence.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "service_bindings")
public class ServiceBindingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "service-binding-uuid")
    @GenericGenerator(name = "service-binding-uuid", strategy = "uuid")
    private String id;

    @Column(name = "service_instance_id")
    private String serviceInstanceId;

    @Column(name = "app_guid")
    private String appGuid;

    public ServiceBindingEntity() {
        // FOR JPA
    }

    public ServiceBindingEntity(String id, String serviceInstanceId, String appGuid) {
        this.id = id;
        this.serviceInstanceId = serviceInstanceId;
        this.appGuid = appGuid;
    }

    public String getId() {
        return id;
    }

    public String getAppGuid() {
        return appGuid;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }
}


