package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.model.Note;
import edu.kpi.notetaker.model.Notebook;
import edu.kpi.notetaker.model.Tag;
import edu.kpi.notetaker.repository.NoteRepository;
import edu.kpi.notetaker.service.NoteService;
import edu.kpi.notetaker.service.NotebookService;
import edu.kpi.notetaker.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NotebookService notebookService;
    private final TagService tagService;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository,
                           NotebookService notebookService,
                           TagService tagService) {
        this.noteRepository = noteRepository;
        this.notebookService = notebookService;
        this.tagService = tagService;
    }

    @Override
    public Note findById(Integer noteId) {
        return noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note with id=" + noteId + " is not found"));
    }

    @Override
    public Collection<Note> findByTagIds(Collection<Integer> tagIds) {
        Collection<Tag> tags = tagService.findByIds(tagIds);
        return noteRepository.findByTagsIn(tags, (long) tags.size());
    }

    @Override
    public Note createNote(Integer notebookId, Note note) {
        Notebook notebook = notebookService.findById(notebookId);
        note.setNotebook(notebook);
        note.setCreationTimestamp(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public void deleteNote(Integer noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public void updateNoteContent(Integer noteId, byte[] content) {
        Note note = findById(noteId);
        note.setContent(content);
        noteRepository.save(note);
    }

    @Override
    public byte[] getNoteContent(Integer noteId) {
        return findById(noteId).getContent();
    }

    @Override
    public void updateTags(Integer noteId, Collection<Integer> tagIds) {
        Note note = findById(noteId);
        Collection<Tag> tags = tagService.findByIds(tagIds);
        note.setTags(tags);
        noteRepository.save(note);
    }
}
