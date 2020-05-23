package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NotebookInputMessage;
import edu.kpi.notetaker.message.NotebookOutputMessage;
import edu.kpi.notetaker.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return NotebookOutputMessage.fromNotebook(notebookService.createNotebook(userId, message.toNotebook()));
    }

    @GetMapping("/{id}")
    public NotebookOutputMessage findNotebook(@PathVariable("id") Integer id){
        return NotebookOutputMessage.fromNotebook(notebookService.findById(id));
    }
}
