package com.example.todolist.service;

import com.example.todolist.dto.FileDTO;
import com.example.todolist.exception.AlreadyDeletedException;
import com.example.todolist.exception.NoSuchFileException;
import com.example.todolist.exception.NoSuchTaskFoundException;
import com.example.todolist.model.FileEntity;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.repository.FileRepository;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private FileService fileService;

    private UUID fileId;
    private UUID taskId;
    private FileEntity fileEntity;
    private TaskEntity taskEntity;
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        fileEntity = new FileEntity("testFile.txt");
        fileEntity.setId(fileId);
        taskEntity = new TaskEntity();
        multipartFile = Mockito.mock(MultipartFile.class);

        // Mock the file repository responses
        Mockito.when(fileRepository.findByIdAndRemovedIsTrue(fileId))
                .thenReturn(Optional.of(fileEntity));
        Mockito.when(fileRepository.findByIdAndRemovedIsFalse(fileId))
                .thenReturn(Optional.of(fileEntity));
        Mockito.when(fileRepository.save(any(FileEntity.class)))
                .thenReturn(fileEntity);

        // Mock the task repository responses
        Mockito.when(taskRepository.findByIdAndRemovedIsFalse(taskId))
                .thenReturn(Optional.of(taskEntity));
    }

    @Test
    void testSoftDelete_WhenFileNotDeleted_ShouldMarkAsDeleted() {
        fileEntity.setRemoved(false);

        fileService.softDelete(fileId);

        assertTrue(fileEntity.getRemoved());
        Mockito.verify(fileRepository).save(fileEntity);
    }

    @Test
    void testSoftDelete_WhenFileAlreadyDeleted_ShouldThrowAlreadyDeletedException() {
        fileEntity.setRemoved(true);

        assertThrows(AlreadyDeletedException.class, () -> fileService.softDelete(fileId));
    }

    @Test
    void testRestore_WhenFileDeleted_ShouldRestore() {
        fileEntity.setRemoved(true);

        fileService.restore(fileId);

        assertFalse(fileEntity.getRemoved());
        Mockito.verify(fileRepository).save(fileEntity);
    }

    @Test
    void testRestore_WhenFileNotDeleted_ShouldThrowNoSuchFileException() {
        fileEntity.setRemoved(false);

        assertThrows(NoSuchFileException.class, () -> fileService.restore(fileId));
    }

    @Test
    void testGetFileName_WhenFileExists_ShouldReturnFileName() {
        fileEntity.setRemoved(false);

        String fileName = fileService.getFileName(fileId);

        assertEquals("testFile.txt", fileName);
    }

    @Test
    void testGetFileName_WhenFileNotFound_ShouldThrowNoSuchFileException() {
        Mockito.when(fileRepository.findByIdAndRemovedIsFalse(fileId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchFileException.class, () -> fileService.getFileName(fileId));
    }

    @Test
    void testSaveFile_ShouldSaveAndReturnFileDTO() {
        FileDTO fileDto = new FileDTO("testFile.txt", taskId);

        FileDTO savedFileDto = fileService.saveFile(fileDto);

        assertEquals(fileDto.getFileName(), savedFileDto.getFileName());
        assertEquals(fileDto.getId(), savedFileDto.getId());
    }

    @Test
    void testAssignFileToTask_ShouldAssignFileToTask() {
        fileEntity.setRemoved(false);

        fileService.assignFile(fileId, taskId);

        assertEquals(taskEntity, fileEntity.getTask());
        Mockito.verify(fileRepository).save(fileEntity);
    }

    @Test
    void testAssignFileToTask_WhenFileNotFound_ShouldThrowNoSuchFileException() {
        Mockito.when(fileRepository.findByIdAndRemovedIsFalse(fileId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchFileException.class, () -> fileService.assignFile(fileId, taskId));
    }

    @Test
    void testAssignFileToTask_WhenTaskNotFound_ShouldThrowNoSuchTaskFoundException() {
        Mockito.when(taskRepository.findByIdAndRemovedIsFalse(taskId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchTaskFoundException.class, () -> fileService.assignFile(fileId, taskId));
    }

    @Test
    void testUploadFile_ShouldSaveAndReturnFileDTO() throws IOException {
        String fileName = "testUploadFile.txt";
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        FileDTO fileDto = fileService.upload(multipartFile, taskId);

        assertNotNull(fileDto);
        assertEquals(fileName, fileDto.getFileName());
        assertEquals(taskId, fileDto.getId());
    }

    @Test
    void testUploadFile_WhenIOExceptionOccurs_ShouldReturnErrorFileDTO() throws IOException {
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("testUploadFile.txt");
        Mockito.doThrow(new IOException("Error")).when(multipartFile).transferTo(any(File.class));

        FileDTO fileDto = fileService.upload(multipartFile, taskId);

        assertEquals("Error", fileDto.getFileName());
        assertEquals(new UUID(0, 0), fileDto.getId());
    }
}
