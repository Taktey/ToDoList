package com.example.todolist.repositories;

import com.example.todolist.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, String> {

    Optional<FileEntity> findByIdAndIsRemovedIsFalse(Long fileId);
}