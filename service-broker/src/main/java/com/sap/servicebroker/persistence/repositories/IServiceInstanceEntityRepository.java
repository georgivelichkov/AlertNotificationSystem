package com.sap.servicebroker.persistence.repositories;

import com.sap.servicebroker.persistence.entities.ServiceInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceInstanceEntityRepository extends JpaRepository<ServiceInstanceEntity, String> {

    boolean existsById(String s);
}
