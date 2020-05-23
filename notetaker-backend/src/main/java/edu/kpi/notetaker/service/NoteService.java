package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Note;

import java.util.Collection;

public interface NoteService {
    Note findById(Integer noteId);

    Collection<Note> findByTagIds(Collection<Integer> tagIds);

    Note createNote(Integer notebookId, Note note);

    void updateNoteContent(Integer noteId, byte[] content);

    byte[] getNoteContent(Integer noteId);

    void updateTags(Integer noteId, Collection<Integer> tagIds);
}
