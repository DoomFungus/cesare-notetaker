package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.UserRepository;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        user.setCreationTimestamp(LocalDateTime.now());
        return userRepository.save(user);
    }
}
