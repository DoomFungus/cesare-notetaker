package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Note;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String title;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime creationTimestamp;

    public static NoteOutputMessage fromNote(Note note){
        NoteOutputMessage message = new NoteOutputMessage();
        message.setId(note.getId());
        message.setTitle(note.getTitle());
        message.setCreationTimestamp(note.getCreationTimestamp());
        return message;
    }
}
