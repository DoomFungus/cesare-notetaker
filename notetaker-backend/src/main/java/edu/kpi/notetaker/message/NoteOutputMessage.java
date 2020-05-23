package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Note;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String title;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationTimestamp;
    @JsonProperty
    private Collection<AttachmentOutputMessage> attachments;
    @JsonProperty
    private Collection<TagOutputMessage> tags;

    public static NoteOutputMessage fromNote(Note note){
        NoteOutputMessage message = new NoteOutputMessage();
        message.setId(note.getId());
        message.setTitle(note.getTitle());
        message.setCreationTimestamp(note.getCreationTimestamp());
        message.setAttachments(note.getAttachments().stream()
                .map(AttachmentOutputMessage::identificationFromAttachment)
                .collect(Collectors.toCollection(ArrayList::new))
        );
        message.setTags(note.getTags().stream()
                .map(TagOutputMessage::identificationFromTag)
                .collect(Collectors.toCollection(ArrayList::new)));
        return message;
    }

    public static NoteOutputMessage identificationFromNote(Note note){
        NoteOutputMessage message = new NoteOutputMessage();
        message.setId(note.getId());
        message.setTitle(note.getTitle());
        return message;
    }
}
