package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NoteInputMessage;
import edu.kpi.notetaker.message.NoteOutputMessage;
import edu.kpi.notetaker.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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
        return NoteOutputMessage
                .fromNote(noteService
                        .createNote(notebookId, message.toNote())
                );
    }

    @GetMapping("/{id}")
    public NoteOutputMessage findNote(@PathVariable("id") Integer id){
        return NoteOutputMessage
                .fromNote(noteService
                        .findById(id)
                );
    }

    @GetMapping
    public Collection<NoteOutputMessage> findNotesByTags(
            @RequestParam("tag_id") Collection<Integer> tagIds){
        return noteService.findByTagIds(tagIds).stream()
                .map(NoteOutputMessage::fromNote)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @PutMapping(value = "/{id}/tags")
    public void updateNoteTags(@PathVariable("id") Integer noteId,
                                  @RequestParam(name = "tag_id") Collection<Integer> tagIds) {
        noteService.updateTags(noteId, tagIds);
    }

    @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateNoteContent(@PathVariable("id") Integer noteId,
                                  @RequestBody MultipartFile content) throws IOException {
        noteService.updateNoteContent(noteId, content.getBytes());
    }

    @GetMapping(value = "/{id}/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ByteArrayResource getNoteContent(@PathVariable("id") Integer id){
        byte[] content = noteService.getNoteContent(id);
        if(content == null) content = new byte[0];
        return new ByteArrayResource(content);
    }
}
