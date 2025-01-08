package com.example.todolist.repositories;

import com.example.todolist.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByIdAndIsRemovedIsFalse(Long userId);

    Optional<UserEntity> findByIdAndIsRemovedIsTrue(Long userId);
}
