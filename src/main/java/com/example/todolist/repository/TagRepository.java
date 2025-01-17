package com.example.todolist.repository;


import com.example.todolist.model.TagEntity;
import com.example.todolist.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, UUID> {

    @Query("SELECT t FROM TaskEntity t JOIN t.tags tag WHERE tag IN :tags")
    List<TaskEntity> findByTagsIn(@Param("tags") List<TagEntity> tags);

    @Query( """ 
            SELECT t FROM TaskEntity t 
            JOIN t.tags tag 
            WHERE tag IN :tags 
            GROUP BY t 
            HAVING COUNT(tag) = :tagCount """)
    List<TaskEntity> findByTagsContainingAll(@Param("tags") List<TagEntity> tags,
                                             @Param("tagCount") long tagCount);

    @Query("SELECT t FROM TagEntity t WHERE t.name IN :names")
    List<TagEntity> findByNameIn(@Param("names") List<String> names);

    Optional<TagEntity> findByName(String name);
}