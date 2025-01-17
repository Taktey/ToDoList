package com.example.todolist.service;

import com.example.todolist.exception.NoSuchUserFoundException;
import com.example.todolist.model.UserEntity;
import com.example.todolist.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /*@Test
    void createUser_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        Long userId = userService.createUser(user);

        assertNotNull(userId);
        assertEquals(1L, userId);
        verify(userRepository, times(1)).save(user);
    }*/

    /*@Test
    void getUserById_Success() throws NoSuchUserFoundException {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findByIdAndRemovedIsFalse(userId)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findByIdAndRemovedIsFalse(userId);
    }*/

    @Test
    void getUserById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndRemovedIsFalse(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals(new NoSuchUserFoundException().getMessage(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndRemovedIsFalse(userId);
    }

    @Test
    void deleteById_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setRemoved(false);

        when(userRepository.findByIdAndRemovedIsFalse(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.deleteById(userId);

        assertTrue(user.getRemoved());
        verify(userRepository, times(1)).findByIdAndRemovedIsFalse(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndRemovedIsFalse(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.deleteById(userId);
        });

        assertEquals(new NoSuchUserFoundException().getMessage(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndRemovedIsFalse(userId);
    }

    @Test
    void restoreById_Success() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setRemoved(true);

        when(userRepository.findByIdAndRemovedIsTrue(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.restoreById(userId);

        assertFalse(user.getRemoved());
        verify(userRepository, times(1)).findByIdAndRemovedIsTrue(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void restoreById_UserNotFound() {
        Long userId = 1L;

        when(userRepository.findByIdAndRemovedIsTrue(userId)).thenReturn(Optional.empty());

        NoSuchUserFoundException exception = assertThrows(NoSuchUserFoundException.class, () -> {
            userService.restoreById(userId);
        });

        assertEquals(new NoSuchUserFoundException().getMessage(), exception.getMessage());
        verify(userRepository, times(1)).findByIdAndRemovedIsTrue(userId);
    }
}