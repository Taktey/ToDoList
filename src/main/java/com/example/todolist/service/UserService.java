package com.example.todolist.service;

import com.example.todolist.Exceptions.NoSuchUserFoundException;
import com.example.todolist.models.UserEntity;
import com.example.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long createUser(UserEntity user) {
        return userRepository.save(user).getId();
    }

    public UserEntity getUserById(Long id) throws NoSuchUserFoundException{
        return userRepository.findByIdAndIsRemovedIsFalse(id)
                .orElseThrow(()->new NoSuchUserFoundException(getUserNotFoundMsg()));
    }


    public void deleteById(Long userId) {
        UserEntity user = userRepository.findByIdAndIsRemovedIsFalse(userId)
                .orElseThrow(()->new NoSuchUserFoundException(getUserNotFoundMsg()));
        user.setIsRemoved(true);
        userRepository.save(user);
    }

    public void restoreById(Long userId) {
        UserEntity user = userRepository.findByIdAndIsRemovedIsTrue(userId)
                .orElseThrow(()->new NoSuchUserFoundException(getUserNotFoundMsg()));
        user.setIsRemoved(false);
        userRepository.save(user);
    }
}
