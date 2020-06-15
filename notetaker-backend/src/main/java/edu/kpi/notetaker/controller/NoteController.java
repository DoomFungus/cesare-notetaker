package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.NoteInputMessage;
import edu.kpi.notetaker.message.NoteOutputMessage;
import edu.kpi.notetaker.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public NoteOutputMessage saveNote(@RequestParam("notebook_id") Integer notebookId,
                               @RequestBody NoteInputMessage message){
        return NoteOutputMessage
                .fromNote(noteService
                        .createNote(notebookId, message.toNote())
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public NoteOutputMessage findNote(@PathVariable("id") Integer id){
        return NoteOutputMessage
                .fromNote(noteService
                        .findById(id)
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable("id") Integer id){
        noteService.deleteNote(id);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public Collection<NoteOutputMessage> findNotesByTags(
            @RequestParam("tag_id") Collection<Integer> tagIds){
        return noteService.findByTagIds(tagIds).stream()
                .map(NoteOutputMessage::identificationFromNote)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(value = "/{id}/tag")
    public void updateNoteTags(@PathVariable("id") Integer noteId,
                                  @RequestParam(name = "tag_id", required = false) List<Integer> tagIds) {
        if(tagIds == null) tagIds =  new ArrayList<>();
        noteService.updateTags(noteId, tagIds);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateNoteContent(@PathVariable("id") Integer noteId,
                                  @RequestBody MultipartFile content) throws IOException {
        noteService.updateNoteContent(noteId, content.getBytes());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/{id}/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ByteArrayResource getNoteContent(@PathVariable("id") Integer id){
        byte[] content = noteService.getNoteContent(id);
        if(content == null) content = new byte[0];
        return new ByteArrayResource(content);
    }
}
