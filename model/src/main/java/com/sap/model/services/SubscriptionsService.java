package com.sap.model.services;

import com.sap.model.exceptions.subscriptions.SubscriptionNotFoundException;
import com.sap.model.interfaces.IDAOService;
import com.sap.persistence.entities.Subscription;
import com.sap.persistence.repositories.ISubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsService implements IDAOService<Subscription> {

    private ISubscriptionRepository subsRepo;

    @Override
    public Subscription get(String id) throws SubscriptionNotFoundException {
        return subsRepo.findById(id).orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
    }

    @Override
    public Subscription getByName( String name) throws SubscriptionNotFoundException {
        return subsRepo.findByName(name).orElseThrow(() -> new SubscriptionNotFoundException("Event not found " + name));
    }

    @Override
    public Page<Subscription> pageOf(int page, int results) {
        return subsRepo.findAll(PageRequest.of(page, results));
    }

    @Override
    public List<Subscription> listAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        subsRepo.findAll().forEach(subscriptions::add);

        return subscriptions;
    }

    @Override
    public Subscription save(Subscription subscription) {
        return subsRepo.save(subscription);
    }

    @Override
    public void delete(Subscription subscription) {
        subsRepo.delete(subscription);
    }

    @Override
    public void findAndDelete(String id) throws SubscriptionNotFoundException {
        Subscription subscription = get(id);
        delete(subscription);
    }

    @Override
    public long count() {
        return subsRepo.count();
    }

    @Autowired
    public void setSubsRepo(ISubscriptionRepository subsRepo) {
        this.subsRepo = subsRepo;
    }
}
