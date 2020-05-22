package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.User;

public interface UserService {
    User findByUsername(String username);

    User createUser(User user);

    User updateUser(User user);
}
