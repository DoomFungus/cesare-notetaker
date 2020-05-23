package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Attachment;

public interface AttachmentService {
    Attachment findById(Integer attachmentId);

    Attachment createAttachment(Integer noteId, Attachment attachment);

    void updateAttachmentContent(Integer attachmentId, byte[] content);

    byte[] getAttachmentContent(Integer attachmentId);
}
