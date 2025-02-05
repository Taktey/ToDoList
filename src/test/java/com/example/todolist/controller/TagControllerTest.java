package com.example.todolist.controller;

import com.example.todolist.dto.TagDTO;
import com.example.todolist.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Mock
    private TagService tagService;

    @InjectMocks
    private TagController tagController;

    private MockMvc mockMvc;
    private UUID tagId;
    private TagDTO tagDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
        tagId = UUID.randomUUID();
        tagDTO = new TagDTO(tagId, "TestTag");
    }

    @Test
    void getAll() throws Exception {
        Set<TagDTO> tags = Set.of(tagDTO);
        when(tagService.getAllTags()).thenReturn(tags);

        mockMvc.perform(get("/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(tagId.toString()))
                .andExpect(jsonPath("$[0].name").value("TestTag"));

        verify(tagService, times(1)).getAllTags();
    }

    @Test
    void create() throws Exception {
        TagDTO newTagDTO = new TagDTO("NewTag");
        TagDTO createdTagDTO = new TagDTO(UUID.randomUUID(), "NewTag");
        when(tagService.createTag(any(TagDTO.class))).thenReturn(createdTagDTO);

        mockMvc.perform(post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"NewTag\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("NewTag"));

        verify(tagService, times(1)).createTag(any(TagDTO.class));
    }
}
