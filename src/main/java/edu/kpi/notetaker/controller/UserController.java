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
        User user = userService.saveUser(message.toUser());
        return UserOutputMessage.fromUser(user);
    }

    @GetMapping
    UserOutputMessage findUser(@RequestParam("username") String username){
        return userService.findByUsername(username)
                .map(UserOutputMessage::fromUser)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
    }
}
