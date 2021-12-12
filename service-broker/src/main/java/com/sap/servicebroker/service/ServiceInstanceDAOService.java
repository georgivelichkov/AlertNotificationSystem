package com.sap.servicebroker.service;

import com.sap.model.interfaces.IDAOService;
import com.sap.servicebroker.persistence.entities.ServiceInstanceEntity;
import com.sap.servicebroker.persistence.repositories.IServiceInstanceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceDoesNotExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ServiceInstanceDAOService implements IDAOService<ServiceInstanceEntity> {

    private IServiceInstanceEntityRepository repo;

    @Autowired
    public void setRepo(IServiceInstanceEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceInstanceEntity get(String id) throws ServiceInstanceDoesNotExistException {
        return repo.findById(id).orElseThrow(() -> new ServiceInstanceDoesNotExistException("ServiceInstance with id:" + id + " does not exist!!!"));
    }

    @Override
    public ServiceInstanceEntity getByName(String name) throws Throwable {
        return null;
    }

    @Override
    public Page<ServiceInstanceEntity> pageOf(int page, int results) {
        return repo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<ServiceInstanceEntity> listAll() {
        return repo.findAll();
    }

    @Override
    public ServiceInstanceEntity save(ServiceInstanceEntity serviceInstanceEntity) {
        return repo.save(serviceInstanceEntity);
    }

    @Override
    public void delete(ServiceInstanceEntity serviceInstanceEntity) {
        repo.delete(serviceInstanceEntity);
    }

    @Override
    public void findAndDelete(String id) throws ServiceInstanceDoesNotExistException {
        repo.delete( get( id ) );
    }

    @Override
    public long count() {
        return repo.count();
    }

    public boolean existsById(String id) {
       return repo.existsById(id);
    }
}
