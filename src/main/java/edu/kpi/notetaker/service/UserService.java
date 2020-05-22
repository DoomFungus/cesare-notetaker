package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.User;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);

    User createUser(User user);

    User updateUserToken(Integer id, String token);
}
