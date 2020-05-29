package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NotebookInputMessage;
import edu.kpi.notetaker.message.NotebookOutputMessage;
import edu.kpi.notetaker.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notebook")
public class NotebookController {
    private final NotebookService notebookService;

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public NotebookOutputMessage saveNotebook(@RequestParam("username") String username,
                                       @RequestBody NotebookInputMessage message){
        return NotebookOutputMessage
                .fromNotebook(notebookService
                        .createNotebook(username, message.toNotebook())
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public NotebookOutputMessage findNotebook(@PathVariable("id") Integer id){
        return NotebookOutputMessage
                .fromNotebook(
                        notebookService.findById(id)
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteNotebook(@PathVariable("id") Integer id){
        notebookService.deleteNotebook(id);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public Collection<NotebookOutputMessage> findNotebooksByUser(@RequestParam("username") String username){
        return notebookService.findAllByUsername(username).stream()
                .map(NotebookOutputMessage::fromNotebook)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
