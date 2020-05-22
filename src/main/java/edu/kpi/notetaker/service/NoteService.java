package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Note;

public interface NoteService {
    Note findById(Integer id);

    Note createNote(Integer notebookId, Note note);

    void updateNoteContent(Integer id, byte[] content);

    byte[] getNoteContent(Integer id);
}
