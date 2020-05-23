package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.AttachmentInput;
import edu.kpi.notetaker.message.AttachmentOutputMessage;
import edu.kpi.notetaker.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttachmentOutputMessage saveNote(@RequestParam("note_id") Integer noteId,
                                             @RequestBody MultipartFile content) throws IOException {
        return AttachmentOutputMessage
                .fromAttachment(attachmentService
                        .createAttachment(noteId, AttachmentInput.toAttachment(content))
                );
    }

    @GetMapping("/{id}")
    public AttachmentOutputMessage findNote(@PathVariable("id") Integer id){
        return AttachmentOutputMessage
                .fromAttachment(attachmentService
                        .findById(id)
                );
    }

    @GetMapping(value = "/{id}/content", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ByteArrayResource getAttachmentContent(@PathVariable("id") Integer id){
        return new ByteArrayResource(attachmentService.getAttachmentContent(id));
    }

    @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateAttachmentContent(@PathVariable("id") Integer attachmentId,
                                  @RequestBody MultipartFile content) throws IOException {
        attachmentService.updateAttachmentContent(attachmentId, content.getBytes());
    }
}
