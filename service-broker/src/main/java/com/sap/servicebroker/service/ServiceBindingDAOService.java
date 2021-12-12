package com.sap.servicebroker.service;

import com.sap.model.interfaces.IDAOService;
import com.sap.servicebroker.persistence.entities.ServiceBindingEntity;
import com.sap.servicebroker.persistence.repositories.IServiceBindingEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.servicebroker.exception.ServiceInstanceBindingDoesNotExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ServiceBindingDAOService implements IDAOService<ServiceBindingEntity> {

    private IServiceBindingEntityRepository repo;

    @Autowired
    public void setRepo(IServiceBindingEntityRepository repo) {
        this.repo = repo;
    }

    @Override
    public ServiceBindingEntity get(String id) throws ServiceInstanceBindingDoesNotExistException {
        return repo.findById(id).orElseThrow(() -> new ServiceInstanceBindingDoesNotExistException(" Service Binding with id: "+ id + " does not exist!!"));
    }

    @Override
    public ServiceBindingEntity getByName(String name) throws Throwable {
        return null;
    }

    @Override
    public Page<ServiceBindingEntity> pageOf(int page, int results) {
        return repo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<ServiceBindingEntity> listAll() {
        return repo.findAll();
    }

    @Override
    public ServiceBindingEntity save(ServiceBindingEntity serviceBindingEntity) {
        return repo.save(serviceBindingEntity);
    }

    @Override
    public void delete(ServiceBindingEntity serviceBindingEntity) {
        repo.delete(serviceBindingEntity);
    }

    @Override
    public void findAndDelete(String id) throws ServiceInstanceBindingDoesNotExistException {
        repo.delete( get(id) );
    }

    @Override
    public long count() {
        return repo.count();
    }

    public boolean existsById(String id) {
        return repo.existsById(id);
    }
}
