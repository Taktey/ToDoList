package com.example.todolist.mapper;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.dto.UserDTO;
import com.example.todolist.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class UserMapper {
    private static final TaskMapper taskMapper = TaskMapper.getInstance();
    private static UserMapper userMapper;

    public static UserMapper getInstance() {
        synchronized (TaskMapper.class) {
            if (userMapper == null) {
                userMapper = new UserMapper();
            }
        }
        return userMapper;
    }
    public UserEntity userDtoToEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
   //             .createdAt(LocalDate.now())
                .tasks(new ArrayList<>())
                .build();
    }

    public UserDTO userEntityToDto(UserEntity user) {
        if (user.getTasks() == null) return new UserDTO(user.getId(), user.getName(), new ArrayList<>());
        List<TaskDTO> tasks = user.getTasks()
                .stream().map(taskMapper::taskEntityToDto)
                .toList();
        return new UserDTO(user.getId(), user.getName(), tasks);
    }

    private UserMapper() {
    }
}
