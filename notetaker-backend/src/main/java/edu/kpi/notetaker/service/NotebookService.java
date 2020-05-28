package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Notebook;

import java.util.Collection;

public interface NotebookService {
    Notebook findById(Integer notebookId);

    Notebook createNotebook(String username, Notebook notebook);

    void deleteNotebook(Integer notebookId);

    Collection<Notebook> findAllByUsername(String username);
}
