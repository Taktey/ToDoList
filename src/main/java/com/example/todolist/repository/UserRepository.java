package com.example.todolist.repository;

import com.example.todolist.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndRemovedIsFalse(Long userId);

    Optional<UserEntity> findByIdAndRemovedIsTrue(Long userId);
}
