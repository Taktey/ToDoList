package com.example.todolist.service;

import com.example.todolist.Exceptions.AlreadyDeletedException;
import com.example.todolist.Exceptions.NoSuchFileException;
import com.example.todolist.Exceptions.NoSuchTaskFoundException;
import com.example.todolist.dto.FileDto;
import com.example.todolist.models.FileEntity;
import com.example.todolist.models.TaskEntity;
import com.example.todolist.repositories.FileRepository;
import com.example.todolist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService extends BaseService {
    private final FileRepository fileRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public FileService(FileRepository fileRepository, TaskRepository taskRepository) {
        this.fileRepository = fileRepository;
        this.taskRepository = taskRepository;
    }

    public void softDeleteFile(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(getFileNotFoundMsg()));
        if(fileEntity.getIsRemoved()){throw new AlreadyDeletedException(getFileAlreadyDeletedMsg());}
        fileEntity.setIsRemoved(true);
        fileRepository.save(fileEntity);
    }

    public String getFileName(Long fileId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(getFileNotFoundMsg()));
        return fileEntity.getFileName();
    }

    public Long saveFile(FileDto fileDto) {
        FileEntity fileEntity = new FileEntity(fileDto.getFileName());
        return fileRepository.save(fileEntity).getId();
    }

    public void assignFile(Long fileId, Long taskId) {
        FileEntity fileEntity = fileRepository.findByIdAndIsRemovedIsFalse(fileId)
                .orElseThrow(()->new NoSuchFileException(getFileNotFoundMsg()));
        TaskEntity taskEntity = taskRepository.findByIdAndIsRemovedIsFalse(taskId)
                .orElseThrow(()->new NoSuchTaskFoundException(getTaskNotFoundMsg()));
        fileEntity.setTask(taskEntity);
        fileRepository.save(fileEntity);
    }
}
