package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.UserInputMessage;
import edu.kpi.notetaker.message.UserOutputMessage;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController()
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    UserOutputMessage saveUser(@RequestBody UserInputMessage message){
        return UserOutputMessage.fromUser(userService.createUser(message.toUser()));
    }

    @GetMapping
    UserOutputMessage findUser(@RequestParam("username") String username){
        return UserOutputMessage.fromUser(userService.findByUsername(username));
    }
}
