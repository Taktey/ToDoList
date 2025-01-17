package com.example.todolist.service;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.exception.NoSuchUserFoundException;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity toBeCreated = UserMapper.userDtoToEntity(userDTO);
        return UserMapper.userEntityToDto(userRepository.save(toBeCreated));
    }

    public UserDTO getUserById(Long id) throws NoSuchUserFoundException {
        return UserMapper.userEntityToDto(userRepository.findByIdAndRemovedIsFalse(id)
                .orElseThrow(NoSuchUserFoundException::new));
    }


    public void deleteById(Long userId) {
        UserEntity user = userRepository.findByIdAndRemovedIsFalse(userId)
                .orElseThrow(NoSuchUserFoundException::new);
        user.setRemoved(true);
        userRepository.save(user);
    }

    public void restoreById(Long userId) {
        UserEntity user = userRepository.findByIdAndRemovedIsTrue(userId)
                .orElseThrow(NoSuchUserFoundException::new);
        user.setRemoved(false);
        userRepository.save(user);
    }
}
