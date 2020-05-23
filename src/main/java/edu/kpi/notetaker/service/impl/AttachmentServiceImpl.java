package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFound;
import edu.kpi.notetaker.model.Attachment;
import edu.kpi.notetaker.model.Note;
import edu.kpi.notetaker.repository.AttachmentRepository;
import edu.kpi.notetaker.service.AttachmentService;
import edu.kpi.notetaker.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final NoteService noteService;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, NoteService noteService) {
        this.attachmentRepository = attachmentRepository;
        this.noteService = noteService;
    }

    @Override
    public Attachment findById(Integer id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Attachment not found"));
    }

    @Override
    public Attachment createAttachment(Integer noteId, Attachment attachment) {
        Note note = noteService.findById(noteId);
        attachment.setNote(note);
        attachment.setCreationTimestamp(LocalDateTime.now());
        return attachmentRepository.save(attachment);
    }

    @Override
    public void updateAttachmentContent(Integer id, byte[] content) {
        Attachment attachment = findById(id);
        attachment.setContent(content);
        attachmentRepository.save(attachment);
    }

    @Override
    public byte[] getAttachmentContent(Integer id) {
        return findById(id).getContent();
    }
}
