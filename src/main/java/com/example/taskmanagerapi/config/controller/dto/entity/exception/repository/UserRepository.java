package com.example.taskmanagerapi.config.controller.dto.entity.exception.repository;  // FIXED: Correct hierarchical package structure

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username. Spring Data JPA automatically creates
     * the implementation for this method based on its name.
     * @param username The username to search for.
     * @return An Optional containing the User if found, otherwise an empty Optional.
     */
    Optional<User> findByUsername(String username);

    // REMOVED: Unnecessary overrides of save() and saveAll() - JpaRepository already provides them correctly:
    // - <S extends User> S save(S entity);  // Inherited and auto-implemented
    // - <S extends User> Iterable<S> saveAll(Iterable<S> entities);  // Inherited for bulk saves
}