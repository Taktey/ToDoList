package com.example.todolist.repository;

import com.example.todolist.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByIdAndRemovedIsFalse(UUID userId);

    Optional<UserEntity> findByIdAndRemovedIsTrue(UUID userId);
}
