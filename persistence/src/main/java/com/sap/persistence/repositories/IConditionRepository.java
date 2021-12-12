package com.sap.persistence.repositories;

import com.sap.persistence.entities.Condition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IConditionRepository extends PagingAndSortingRepository<Condition, String> {
    Optional<Condition> findByName(String name);
}
