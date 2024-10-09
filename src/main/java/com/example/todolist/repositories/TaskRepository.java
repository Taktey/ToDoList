package com.example.todolist.repositories;

import com.example.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndIsRemovedIsFalse(Long taskId);
}
