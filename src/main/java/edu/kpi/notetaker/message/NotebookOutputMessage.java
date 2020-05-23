package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Notebook;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotebookOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String title;
    @JsonProperty(value = "encryption_key")
    private String encryptionKey;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationTimestamp;
    @JsonProperty("notes")
    private Collection<NoteOutputMessage> notes;

    public static NotebookOutputMessage fromNotebook(Notebook notebook){
        NotebookOutputMessage message = new NotebookOutputMessage();
        message.setId(notebook.getId());
        message.setTitle(notebook.getTitle());
        message.setEncryptionKey(notebook.getEncryptionKey());
        message.setCreationTimestamp(notebook.getCreationTimestamp());
        message.setNotes(notebook.getNotes()
                .stream()
                .map(NoteOutputMessage::identificationFromNote)
                .collect(
                        Collectors.toCollection(ArrayList::new)
                )
        );
        return message;
    }

    public static NotebookOutputMessage identificationFromNotebook(Notebook notebook){
        NotebookOutputMessage message = new NotebookOutputMessage();
        message.setId(notebook.getId());
        message.setTitle(notebook.getTitle());
        return message;
    }
}
