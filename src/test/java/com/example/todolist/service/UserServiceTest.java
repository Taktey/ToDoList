package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.models.UserEntity;
import com.example.todolist.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        Long userId = userService.createUser(user);

        assertNotNull(userId);
        assertEquals(1L, userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void getUserById_Success() throws NoSuchUserFoundException {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findByIdAndIsRemovedIsFalse(userId)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsFalse(userId);
    }

    @Test
    void getUserById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndIsRemovedIsFalse(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals(new BaseService().getUserNotFoundMsg(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsFalse(userId);
    }

    @Test
    void deleteById_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setIsRemoved(false);

        when(userRepository.findByIdAndIsRemovedIsFalse(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.deleteById(userId);

        assertTrue(user.getIsRemoved());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsFalse(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndIsRemovedIsFalse(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.deleteById(userId);
        });

        assertEquals(new BaseService().getUserNotFoundMsg(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsFalse(userId);
    }

    @Test
    void restoreById_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setIsRemoved(true);

        when(userRepository.findByIdAndIsRemovedIsTrue(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.restoreById(userId);

        assertFalse(user.getIsRemoved());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsTrue(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void restoreById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndIsRemovedIsTrue(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.restoreById(userId);
        });

        assertEquals(new BaseService().getUserNotFoundMsg(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndIsRemovedIsTrue(userId);
    }
}