package com.example.todolist.repositories;


import com.example.todolist.models.TagEntity;
import com.example.todolist.models.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("SELECT t FROM TaskEntity t JOIN t.tags tag WHERE tag IN :tags")
    List<TaskEntity> findByTagsIn(@Param("tags") List<TagEntity> tags);
    @Query("SELECT t FROM TagEntity t WHERE t.name IN :names")
    List<TagEntity> findByNameIn(@Param("names") List<String> names);

    Optional<TagEntity>findByName(String name);
}