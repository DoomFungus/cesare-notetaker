package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Note;
import lombok.Data;

@Data
public class NoteInputMessage {
    @JsonProperty
    private String title;

    public Note toNote(){
        Note note = new Note();
        note.setTitle(this.getTitle());
        return note;
    }
}
