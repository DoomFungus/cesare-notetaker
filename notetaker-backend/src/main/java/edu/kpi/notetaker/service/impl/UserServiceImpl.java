package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityAlreadyExistsException;
import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.UserRepository;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id=" + userId + " is  not found"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with username=" + username + " is not found"));
    }

    @Override
    public User createUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(x -> {
                    throw new EntityAlreadyExistsException("User with username=" + user.getUsername() + " already exists");
                });
        user.setCreationTimestamp(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User updateUserToken(String userName, String token) {
        User user = findByUsername(userName);
        user.setRefreshToken(token);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            return findByUsername(s);
        } catch (EntityNotFoundException ex){
            throw new UsernameNotFoundException(ex.getMessage(), ex);
        }
    }
}
