package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Tag;
import lombok.Data;

@Data
public class TagInputMessage {
    @JsonProperty
    private String name;

    public Tag toTag(){
        Tag tag = new Tag();
        tag.setName(name);
        return tag;
    }
}
