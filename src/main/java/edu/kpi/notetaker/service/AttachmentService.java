package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Attachment;

public interface AttachmentService {
    Attachment findById(Integer id);

    Attachment createAttachment(Integer noteId, Attachment attachment);

    void updateAttachmentContent(Integer id, byte[] content);

    byte[] getAttachmentContent(Integer id);
}
