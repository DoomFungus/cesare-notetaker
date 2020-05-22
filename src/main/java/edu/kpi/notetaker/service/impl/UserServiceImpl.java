package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityAlreadyExists;
import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFound;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.UserRepository;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("User not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFound("User not found"));
    }

    @Override
    public User createUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(x -> {
                    throw new EntityAlreadyExists("User already exists");
                });
        user.setCreationTimestamp(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUserToken(Integer id, String token) {
        User user = findById(id);
        user.setRefreshToken(token);
        return userRepository.save(user);
    }
}
