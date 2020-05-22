package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFound;
import edu.kpi.notetaker.model.Notebook;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.NotebookRepository;
import edu.kpi.notetaker.service.NotebookService;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepository;
    private final UserService userService;

    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepository, UserService userService) {
        this.notebookRepository = notebookRepository;
        this.userService = userService;
    }

    @Override
    public Notebook findById(Integer id) {
        return notebookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Notebook not found"));
    }

    @Override
    public Notebook createNotebook(Integer userId, Notebook notebook) {
        User author = userService.findById(userId);
        notebook.setUser(author);
        notebook.setCreationTimestamp(LocalDateTime.now());
        return notebookRepository.save(notebook);
    }
}
