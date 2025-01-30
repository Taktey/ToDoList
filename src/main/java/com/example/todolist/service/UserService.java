package com.example.todolist.service;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.exception.NoSuchUserFoundException;
import com.example.todolist.mapper.UserMapper;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper = UserMapper.getInstance();

    public UserDTO createUser(UserDTO userDTO) {
        UserEntity toBeCreated = userMapper.userDtoToEntity(userDTO);
        toBeCreated.setCreatedAt(LocalDate.now());
        return userMapper.userEntityToDto(userRepository.save(toBeCreated));
    }

    public UserDTO getUserById(UUID id) throws NoSuchUserFoundException {
        return userMapper.userEntityToDto(userRepository.findByIdAndRemovedIsFalse(id)
                .orElseThrow(NoSuchUserFoundException::new));
    }


    public void deleteById(UUID userId) {
        UserEntity user = userRepository.findByIdAndRemovedIsFalse(userId)
                .orElseThrow(NoSuchUserFoundException::new);
        user.setRemoved(true);
        userRepository.save(user);
    }

    public void restoreById(UUID userId) {
        UserEntity user = userRepository.findByIdAndRemovedIsTrue(userId)
                .orElseThrow(NoSuchUserFoundException::new);
        user.setRemoved(false);
        userRepository.save(user);
    }

    public void saveChanges(UserEntity user) {
        if (user.getCreatedAt() == null) {
            UserEntity userInBase = userRepository.findByIdAndRemovedIsFalse(user.getId())
                    .orElseThrow(() -> new NoSuchUserFoundException("The modified UserEntity does not exist or has been deleted."));
            user.setCreatedAt(userInBase.getCreatedAt());
        }
        userRepository.save(user);
    }
}
