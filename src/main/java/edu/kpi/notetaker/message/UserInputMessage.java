package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.User;
import lombok.Data;

@Data
public class UserInputMessage {
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;

    public User toUser(){
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        return user;
    }
}
