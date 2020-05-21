package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserOutputMessage {
    @JsonProperty
    private String username;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime creationTimestamp;

    public static UserOutputMessage fromUser(User user){
        UserOutputMessage message = new UserOutputMessage();
        message.setUsername(user.getUsername());
        message.setCreationTimestamp(user.getCreationTimestamp());
        return message;
    }
}
