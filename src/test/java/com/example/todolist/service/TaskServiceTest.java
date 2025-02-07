package com.example.todolist.service;

import com.example.todolist.model.FileEntity;
import com.example.todolist.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskService taskService;

    private UUID fileId;
    private UUID taskId;
    private FileEntity fileEntity;

    @BeforeEach
    void setUp(){
        fileId = UUID.randomUUID();
        taskId = UUID.randomUUID();
        fileEntity = new FileEntity("Test_file");
        fileEntity.setId(fileId);
    }

   /* @Test
    void*/

}
