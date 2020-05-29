package edu.kpi.notetaker.controller;

import edu.kpi.notetaker.message.TagInputMessage;
import edu.kpi.notetaker.message.TagOutputMessage;
import edu.kpi.notetaker.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public TagOutputMessage saveTag(@RequestParam("user_id") Integer userId,
                                              @RequestBody TagInputMessage message){
        return TagOutputMessage
                .fromTag(tagService
                        .createTag(userId, message.toTag())
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public TagOutputMessage findTag(@PathVariable("id") Integer id){
        return TagOutputMessage
                .fromTag(
                        tagService.findById(id)
                );
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping()
    public Collection<TagOutputMessage> findTagsByUser(
            @RequestParam("user_id") Integer userId){
        return tagService.findByUserId(userId).stream()
                .map(TagOutputMessage::fromTag)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
