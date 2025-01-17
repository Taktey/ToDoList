package com.example.todolist.service;

import com.example.todolist.dto.TagDTO;
import com.example.todolist.dto.TaskDTO;
import com.example.todolist.exception.NoSuchTagFoundException;
import com.example.todolist.mapper.TaskMapper;
import com.example.todolist.model.TagEntity;
import com.example.todolist.model.TaskEntity;
import com.example.todolist.repository.TagRepository;
import com.example.todolist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final TaskMapper taskMapper = TaskMapper.getInstance();

    public Set<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
                .map(e -> new TagDTO(e.getId(), e.getName()))
                .collect(toSet());
    }

    public Set<TaskDTO> getTasksHaveTags(List<String> tags) {
        List<TagEntity> tagEntities = tags.stream()
                .map(tag -> tagRepository.findByName(tag)
                        .orElseThrow(NoSuchTagFoundException::new))
                .collect(toList());
        List<TaskEntity> taskEntities = tagRepository.findByTagsContainingAll(tagEntities, tagEntities.size());
        return taskEntities.stream()
                .map(taskMapper::taskEntityToDto)
                .collect(toSet());
    }

    public TagDTO createTag(TagDTO tagDTO) {
        if (!existsByName(tagDTO.getName())) {
            TagEntity tag = saveNewTag(tagDTO.getName());
            return new TagDTO(tag.getId(), tag.getName());
        } else throw new RuntimeException("Тег '" + tagDTO.getName().toLowerCase() + "' уже существует");
    }

    public Set<TagEntity> getOrCreateTags(Set<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> saveNewTag(tagName)))
                .collect(toSet());
    }

    private boolean existsByName(String tagName) {
        return tagRepository.findByName(tagName.toLowerCase()).isPresent();
    }

    private TagEntity saveNewTag(String tagName) {
        return tagRepository.save(new TagEntity(tagName.toLowerCase()));
    }


}
