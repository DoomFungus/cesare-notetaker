package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthInputMessage {
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
}
