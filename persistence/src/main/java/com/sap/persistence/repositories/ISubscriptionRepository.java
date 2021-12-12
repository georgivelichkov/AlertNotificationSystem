package com.sap.persistence.repositories;

import com.sap.persistence.entities.Subscription;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISubscriptionRepository extends PagingAndSortingRepository<Subscription, String> {
    Optional<Subscription> findByName(String name);
}
