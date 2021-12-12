package com.sap.servicebroker.persistence.repositories;

import com.sap.servicebroker.persistence.entities.ServiceBindingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceBindingEntityRepository extends JpaRepository<ServiceBindingEntity, String> {
}
