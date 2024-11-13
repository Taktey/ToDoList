package com.example.todolist.repositories;

import com.example.todolist.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    Optional<TaskEntity> findByIdAndIsRemovedIsFalse(Long taskId);

    @Query("SELECT t FROM task_entity t JOIN t.tags tag WHERE tag.name IN :tags")
    List<TaskEntity> findByTags(@Param("tags") Set<String> tags);
}
