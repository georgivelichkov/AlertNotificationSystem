package com.sap.persistence.repositories;

import com.sap.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String name);
}
