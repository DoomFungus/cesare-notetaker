package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    User saveUser(User user);
}
