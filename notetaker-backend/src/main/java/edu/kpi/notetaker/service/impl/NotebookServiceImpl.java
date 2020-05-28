package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.model.Notebook;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.NotebookRepository;
import edu.kpi.notetaker.service.NotebookService;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

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
    public Notebook findById(Integer notebookId) {
        return notebookRepository.findById(notebookId)
                .orElseThrow(() -> new EntityNotFoundException("Notebook with id=" + notebookId + " is  not found"));
    }

    @Override
    public Notebook createNotebook(String username, Notebook notebook) {
        User author = userService.findByUsername(username);
        notebook.setUser(author);
        notebook.setCreationTimestamp(LocalDateTime.now());
        return notebookRepository.save(notebook);
    }

    @Override
    public void deleteNotebook(Integer notebookId) {
        notebookRepository.deleteById(notebookId);
    }

    @Override
    public Collection<Notebook> findAllByUsername(String username) {
        User author = userService.findByUsername(username);
        return notebookRepository.findAllByUser(author);
    }
}
