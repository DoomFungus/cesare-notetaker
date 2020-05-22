package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.UserInputMessage;
import edu.kpi.notetaker.message.UserOutputMessage;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserOutputMessage saveUser(@RequestBody UserInputMessage message){
        return UserOutputMessage.fromUser(userService.createUser(message.toUser()));
    }

    @GetMapping
    public UserOutputMessage findUser(@RequestParam("username") String username){
        return UserOutputMessage.fromUser(userService.findByUsername(username));
    }
}
