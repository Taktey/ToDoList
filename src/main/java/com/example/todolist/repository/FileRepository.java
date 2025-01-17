package com.example.todolist.repository;

import com.example.todolist.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    Optional<FileEntity> findByIdAndRemovedIsFalse(UUID fileId);

    Optional<FileEntity> findByIdAndRemovedIsTrue(UUID fileId);
}