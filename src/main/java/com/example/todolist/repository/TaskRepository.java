package com.example.todolist.repository;

import com.example.todolist.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {

    Optional<TaskEntity> findByIdAndRemovedIsFalse(UUID Id);

    Optional<TaskEntity> findByIdAndRemovedIsTrue(UUID Id);

    Set<TaskEntity> findByUserAndRemovedIsFalse(UUID userId);
}
