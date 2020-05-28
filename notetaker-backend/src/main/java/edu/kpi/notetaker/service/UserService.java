package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findById(Integer userId);

    User findByUsername(String username);

    User createUser(User user);

    User updateUserToken(String username, String token);
}
