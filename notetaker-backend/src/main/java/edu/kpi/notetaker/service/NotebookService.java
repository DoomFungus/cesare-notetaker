package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Notebook;

import java.util.Collection;

public interface NotebookService {
    Notebook findById(Integer notebookId);

    Notebook createNotebook(Integer userId, Notebook notebook);

    Collection<Notebook> findAllByUserId(Integer userId);
}
