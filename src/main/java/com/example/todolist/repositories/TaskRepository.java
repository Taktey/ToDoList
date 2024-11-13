package com.example.todolist.repositories;

import com.example.todolist.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndIsRemovedIsFalse(Long taskId);
}
