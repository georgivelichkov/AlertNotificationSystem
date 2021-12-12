package com.sap.persistence.repositories;

import com.sap.persistence.entities.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEventRepository extends MongoRepository<Event, ObjectId> {

    Optional<Event> findByType(String name);

}
