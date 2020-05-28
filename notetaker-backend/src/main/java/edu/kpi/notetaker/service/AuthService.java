package edu.kpi.notetaker.service;

import edu.kpi.notetaker.message.AuthOutputMessage;

public interface AuthService {
    AuthOutputMessage signIn(String username, String password);

    AuthOutputMessage signUp(String username, String password);

    String refresh(String refreshToken);

    void signout();
}
