package edu.kpi.notetaker.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kpi.notetaker.model.Attachment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentOutputMessage {
    @JsonProperty
    private Integer id;
    @JsonProperty
    private String title;
    @JsonProperty("creation_timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationTimestamp;

    public static AttachmentOutputMessage fromAttachment(Attachment attachment){
        AttachmentOutputMessage message = new AttachmentOutputMessage();
        message.setId(attachment.getId());
        message.setTitle(attachment.getTitle());
        message.setCreationTimestamp(attachment.getCreationTimestamp());
        return message;
    }

    public static AttachmentOutputMessage identificationFromAttachment(Attachment attachment){
        AttachmentOutputMessage message = new AttachmentOutputMessage();
        message.setId(attachment.getId());
        message.setTitle(attachment.getTitle());
        return message;
    }
}
