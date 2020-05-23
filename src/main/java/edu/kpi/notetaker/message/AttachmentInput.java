package edu.kpi.notetaker.message;

import edu.kpi.notetaker.model.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AttachmentInput {
    public static Attachment toAttachment(MultipartFile multipartFile) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setTitle(multipartFile.getName());
        attachment.setContent(multipartFile.getBytes());
        return attachment;
    }
}
