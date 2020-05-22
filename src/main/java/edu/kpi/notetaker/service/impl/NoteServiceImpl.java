package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFound;
import edu.kpi.notetaker.model.Note;
import edu.kpi.notetaker.model.Notebook;
import edu.kpi.notetaker.repository.NoteRepository;
import edu.kpi.notetaker.service.NoteService;
import edu.kpi.notetaker.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NotebookService notebookService;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NotebookService notebookService) {
        this.noteRepository = noteRepository;
        this.notebookService = notebookService;
    }

    @Override
    public Note findById(Integer id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Note not found"));
    }

    @Override
    public Note createNote(Integer notebookId, Note note) {
        Notebook notebook = notebookService.findById(notebookId);
        note.setNotebook(notebook);
        note.setCreationTimestamp(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public void updateNoteContent(Integer id, byte[] content) {
        Note note = findById(id);
        note.setContent(content);
        noteRepository.save(note);
    }

    @Override
    public byte[] getNoteContent(Integer id) {
        return findById(id).getContent();
    }
}
