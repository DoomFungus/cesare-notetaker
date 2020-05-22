package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Notebook;

public interface NotebookService {
    Notebook findById(Integer id);

    Notebook createNotebook(Integer userId, Notebook notebook);
}
