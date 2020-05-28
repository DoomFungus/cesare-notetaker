package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthOutputMessage {
    @JsonProperty
    private String accessToken;
    @JsonProperty
    private String refreshToken;
}
