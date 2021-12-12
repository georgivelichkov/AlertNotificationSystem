package com.sap.persistence.repositories;

import com.sap.persistence.entities.Action;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IActionRepository extends PagingAndSortingRepository<Action, String> {

     Optional<Action> findById(UUID id);
     Optional<Action> findByName(String name);

}
