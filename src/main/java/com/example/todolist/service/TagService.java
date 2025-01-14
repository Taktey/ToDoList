package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchTagFoundException;
import com.example.todolist.dto.TagCreateDto;
import com.example.todolist.dto.TagDto;
import com.example.todolist.dto.TaskDto;
import com.example.todolist.models.TagEntity;
import com.example.todolist.models.TaskEntity;
import com.example.todolist.repositories.TagRepository;
import com.example.todolist.repositories.TaskRepository;
import com.example.todolist.util.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService extends BaseService{
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TaskRepository taskRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    public List<TaskDto> getTasksHaveTags(List<String> tags){
        List<TagEntity> tagEntities = tags.stream()
                .map(tag->tagRepository.findByName(tag)
                        .orElseThrow(()-> new NoSuchTagFoundException(getTagNotFoundMsg()+tag)))
                .collect(Collectors.toList());
        List<TaskEntity> taskEntities = tagRepository.findByTagsContainingAll(tagEntities, tagEntities.size());
        return taskEntities.stream()
                .map(TaskMapper::taskEntityToDto).collect(Collectors.toList());
    }

    public TagDto createTag(TagCreateDto tagToCreate){
        if(!existsByName(tagToCreate.getName())){
            TagEntity tag = saveNewTag(tagToCreate.getName());
            return new TagDto(tag.getId(), tag.getName());
        } else throw new RuntimeException("Тэг '"+tagToCreate.getName().toLowerCase()+"' уже существует");
    }

    public Set<TagEntity> getOrCreateTags(Set<String> tagNames){
        return tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> saveNewTag(tagName)))
                .collect(Collectors.toSet());
    }

    private boolean existsByName(String tagName) {
        return tagRepository.findByName(tagName.toLowerCase()).isPresent();
    }
    private TagEntity saveNewTag(String tagName){
        return tagRepository.save(new TagEntity(tagName.toLowerCase()));
    }
}
