package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NoteInputMessage;
import edu.kpi.notetaker.message.NoteOutputMessage;
import edu.kpi.notetaker.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public NoteOutputMessage saveNote(@RequestParam("notebook_id") Integer notebookId,
                               @RequestBody NoteInputMessage message){
        return NoteOutputMessage.fromNote(noteService.createNote(notebookId, message.toNote()));
    }

    @GetMapping
    public NoteOutputMessage findNote(@RequestParam("id") Integer id){
        return NoteOutputMessage.fromNote(noteService.findById(id));
    }

    @PutMapping(value = "/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateNoteContent(@RequestParam("id") Integer noteId,
                                  @RequestBody MultipartFile content) throws IOException {
        noteService.updateNoteContent(noteId, content.getBytes());
    }

    @GetMapping(value = "/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ByteArrayResource getNoteContent(@RequestParam("id") Integer id){
        HttpHeaders headers = new HttpHeaders();
        return new ByteArrayResource(noteService.getNoteContent(id));
    }
}
