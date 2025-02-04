package com.example.todolist.controller;

import com.example.todolist.dto.FileDTO;
import com.example.todolist.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class FileControllerTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileController fileController;

    private UUID fileId;
    private UUID taskId;
    private MultipartFile multipartFile;
    private FileDTO fileDTO;

    @BeforeEach
    void setUp() {
        fileId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        multipartFile = new MockMultipartFile("file", "test.pdf", MediaType.APPLICATION_PDF_VALUE, "dummy content".getBytes());
        fileDTO = new FileDTO();
        fileDTO.setId(fileId);
        fileDTO.setFileName("test.pdf");
        fileDTO.setTaskId(taskId);
    }

    @Test
    @DisplayName("Тест загрузки файла")
    void downloadTest() {
        Resource resource = mock(Resource.class);
        when(fileService.download(fileId)).thenReturn(resource);
        ResponseEntity<Resource> response = fileController.download(fileId);
        assertEquals(OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals(resource, response.getBody());
        verify(fileService, times(1)).download(fileId);
    }

    @Test
    @DisplayName("Тест удаления файла")
    void deleteTest() {
        doNothing().when(fileService).softDelete(fileId);
        fileController.delete(fileId);
        verify(fileService, times(1)).softDelete(fileId);
    }

    @Test
    @DisplayName("Тест восстановления файла")
    void restoreTest() {
        doNothing().when(fileService).restore(fileId);
        fileController.restore(fileId);
        verify(fileService, times(1)).restore(fileId);
    }

    @Test
    @DisplayName("Тест привязки файла к задаче")
    void assignToTaskTest() {
        doNothing().when(fileService).assignFile(fileId, taskId);
        fileController.assignToTask(fileId, taskId);
        verify(fileService, times(1)).assignFile(fileId, taskId);
    }

    @Test
    @DisplayName("Тест загрузки файла в систему")
    void uploadTest() {
        when(fileService.upload(multipartFile, taskId)).thenReturn(fileDTO);
        FileDTO response = fileController.upload(multipartFile, taskId);
        assertNotNull(response);
        assertEquals(fileDTO.getId(), response.getId());
        assertEquals(fileDTO.getFileName(), response.getFileName());
        assertEquals(fileDTO.getTaskId(), response.getTaskId());
        verify(fileService, times(1)).upload(multipartFile, taskId);
    }
}
