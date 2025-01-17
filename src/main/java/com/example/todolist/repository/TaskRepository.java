package com.example.todolist.repository;

import com.example.todolist.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndRemovedIsFalse(Long taskId);

    Optional<TaskEntity> findByIdAndRemovedIsTrue(Long taskId);
}
