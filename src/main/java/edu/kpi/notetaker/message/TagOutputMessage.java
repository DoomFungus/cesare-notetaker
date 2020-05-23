package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Tag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String name;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationTimestamp;

    public static TagOutputMessage fromTag(Tag tag){
        TagOutputMessage message = new TagOutputMessage();
        message.setId(tag.getId());
        message.setName(tag.getName());
        message.setCreationTimestamp(tag.getCreationTimestamp());
        return message;
    }

    public static TagOutputMessage identificationFromTag(Tag tag){
        TagOutputMessage message = new TagOutputMessage();
        message.setId(tag.getId());
        message.setName(tag.getName());
        return message;
    }
}
