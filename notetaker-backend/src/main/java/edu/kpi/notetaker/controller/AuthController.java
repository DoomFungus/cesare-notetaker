package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.AuthInputMessage;
import edu.kpi.notetaker.message.AuthOutputMessage;
import edu.kpi.notetaker.message.UserInputMessage;
import edu.kpi.notetaker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public AuthOutputMessage signIn(@RequestBody AuthInputMessage message){
        return authService.signIn(message.getUsername(), message.getPassword());
    }

    @PostMapping("/signup")
    public AuthOutputMessage signUp(@RequestBody AuthInputMessage message){
        return authService.signUp(message.getUsername(), message.getPassword());
    }

    @PostMapping("/refresh")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String refresh(@RequestBody String refreshToken){
        return authService.refresh(refreshToken);
    }

    @PostMapping("/signout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void signout(){
        authService.signout();
    }
}
