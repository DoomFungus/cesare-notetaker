package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.AttachmentInput;
import edu.kpi.notetaker.message.AttachmentOutputMessage;
import edu.kpi.notetaker.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttachmentOutputMessage saveAttachment(@RequestParam("note_id") Integer noteId,
                                             @RequestBody MultipartFile content) throws IOException {
        return AttachmentOutputMessage
                .fromAttachment(attachmentService
                        .createAttachment(noteId, AttachmentInput.toAttachment(content))
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public AttachmentOutputMessage findAttachment(@PathVariable("id") Integer id){
        return AttachmentOutputMessage
                .fromAttachment(attachmentService
                        .findById(id)
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteAttachment(@PathVariable("id") Integer id){
        attachmentService.deleteAttachment(id);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping(value = "/{id}/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ByteArrayResource getAttachmentContent(@PathVariable("id") Integer id){
        return new ByteArrayResource(attachmentService.getAttachmentContent(id));
    }
}
