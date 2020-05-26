package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NotebookInputMessage;
import edu.kpi.notetaker.message.NotebookOutputMessage;
import edu.kpi.notetaker.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public NotebookOutputMessage saveNotebook(@RequestParam("user_id") Integer userId,
                                       @RequestBody NotebookInputMessage message){
        return NotebookOutputMessage
                .fromNotebook(notebookService
                        .createNotebook(userId, message.toNotebook())
                );
    }

    @GetMapping("/{id}")
    public NotebookOutputMessage findNotebook(@PathVariable("id") Integer id){
        return NotebookOutputMessage
                .fromNotebook(
                        notebookService.findById(id)
                );
    }

    @DeleteMapping("/{id}")
    public void deleteNotebook(@PathVariable("id") Integer id){
        notebookService.deleteNotebook(id);
    }

    @GetMapping
    public Collection<NotebookOutputMessage> findNotebooksByUser(@RequestParam("user_id") Integer id){
        return notebookService.findAllByUserId(id).stream()
                .map(NotebookOutputMessage::fromNotebook)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
