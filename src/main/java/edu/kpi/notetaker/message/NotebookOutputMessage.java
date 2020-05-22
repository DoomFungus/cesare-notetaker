package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Note;
import edu.kpi.notetaker.model.Notebook;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class NotebookOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String title;
    @JsonProperty(value = "encryption_key")
    private String encryptionKey;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime creationTimestamp;
    @JsonProperty("note_ids")
    private Collection<Integer> noteIds;

    public static NotebookOutputMessage fromNotebook(Notebook notebook){
        NotebookOutputMessage message = new NotebookOutputMessage();
        message.setId(notebook.getId());
        message.setTitle(notebook.getTitle());
        message.setEncryptionKey(notebook.getEncryptionKey());
        message.setCreationTimestamp(notebook.getCreationTimestamp());
        message.setNoteIds(notebook.getNotes()
                .stream()
                .map(Note::getId)
                .collect(
                        Collectors.toCollection(ArrayList::new)
                )
        );
        return message;
    }
}
