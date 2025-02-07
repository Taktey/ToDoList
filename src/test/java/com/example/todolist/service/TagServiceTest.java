package com.example.todolist.service;

import com.example.todolist.dto.TagDTO;
import com.example.todolist.dto.TaskDTO;
import com.example.todolist.exception.NoSuchTagFoundException;
import com.example.todolist.exception.TagAlreadyExistsException;
import com.example.todolist.model.TagEntity;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.TagRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private TagEntity tagEntity;
    private UUID tagId;
    private String tagName;

    @BeforeEach
    void setUp() {
        tagId = UUID.randomUUID();
        tagName = RandomStringUtils.randomAlphabetic(5).toLowerCase();
        tagEntity = new TagEntity(tagId, tagName);
    }

    @Test
    void getAllTags() {
        when(tagRepository.findAll()).thenReturn(List.of(tagEntity, tagEntity));
        Set<TagDTO> result = tagService.getAllTags();
        assertEquals(2, result.size());
        result.forEach(e -> {
            assertEquals(tagId, e.getId());
            assertEquals(tagName, e.getName());
        });
    }

    @Test
    void getTasksHaveTags() {
        List<String> tagNames = List.of(tagName);
        TaskEntity taskEntity = new TaskEntity();
        UUID taskId = UUID.randomUUID();
        taskEntity.setId(taskId);
        taskEntity.setDescription("Test task");
        taskEntity.setUser(new UserEntity());
        tagEntity.setTasks(Set.of(taskEntity));

        when(tagRepository.findByName(tagName))
                .thenReturn(Optional.of(tagEntity));
        when(tagRepository.findByTagsContainingAll(List.of(tagEntity), 1))
                .thenReturn(List.of(taskEntity, taskEntity, taskEntity));

        Set<TaskDTO> result = tagService.getTasksHaveTags(tagNames);
        assertEquals(3, result.size());
        result.forEach(e -> {
            assertEquals(taskId, e.getId());
            assertEquals("Test task", e.getDescription());
        });
    }

    @Test
    @DisplayName("Метод получения задач по тегам, теги не найдены")
    void getTasksHaveTagsNotFound() {
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());
        assertThrows(NoSuchTagFoundException.class,
                () -> tagService.getTasksHaveTags(List.of(tagName)));
    }

    @Test
    void createTag() {
        TagDTO tagDTO = new TagDTO(tagId, tagName);

        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());
        when(tagRepository.save(any(TagEntity.class))).thenAnswer(invocationOnMock -> {
            TagEntity savedTag = invocationOnMock.getArgument(0);
            savedTag.setId(tagId);
            return savedTag;
        });
        TagDTO result = tagService.createTag(tagDTO);
        assertEquals(tagId, result.getId());
        assertEquals(tagName, result.getName());
    }

    @Test
    @DisplayName("Метод создания тега, тег уже существует")
    void createTagAlreadyExists() {
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(tagEntity));
        assertThrows(TagAlreadyExistsException.class,
                () -> tagService.createTag(new TagDTO(tagName)));
    }


    @Test
    void getOrCreateTags() {
        String tag1 = tagName;
        String tag2 = "some tag";

        when(tagRepository.findByName(tag1)).thenReturn(Optional.of(tagEntity));
        when(tagRepository.findByName(tag2)).thenReturn(Optional.empty());
        when(tagRepository.save(any(TagEntity.class)))
                .thenAnswer(invocationOnMock -> {
                    TagEntity savedTag = invocationOnMock.getArgument(0);
                    savedTag.setId(tagId);
                    return savedTag;
                });
        Set<TagEntity> result = tagService.getOrCreateTags(Set.of(tag1, tag2));
        assertTrue(result.stream().anyMatch(tag -> tag.getName().equals(tag1)));
        assertTrue(result.stream().anyMatch(tag -> tag.getName().equals(tag2)));
    }
}