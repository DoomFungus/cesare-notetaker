package edu.kpi.notetaker.exceptionhandling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    @JsonProperty(index = 0)
    private final LocalDateTime timestamp;
    @JsonIgnore
    private final HttpStatus httpStatus;
    @JsonProperty(index = 3)
    private final String message;
    @JsonProperty(index = 4)
    private final String path;

    @JsonProperty(index = 1)
    public Integer getStatus(){
        return httpStatus.value();
    }

    @JsonProperty(index = 2)
    public String getError(){
        return httpStatus.getReasonPhrase();
    }
}
