package edu.kpi.notetaker.service.impl;

import edu.kpi.notetaker.exceptionhandling.exceptions.EntityNotFoundException;
import edu.kpi.notetaker.model.Tag;
import edu.kpi.notetaker.model.User;
import edu.kpi.notetaker.repository.TagRepository;
import edu.kpi.notetaker.service.TagService;
import edu.kpi.notetaker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final UserService userService;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, UserService userService) {
        this.tagRepository = tagRepository;
        this.userService = userService;
    }


    @Override
    public Tag findById(Integer tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new EntityNotFoundException("Tag with id=" + tagId + " is not found"));
    }

    @Override
    public Collection<Tag> findByIds(Collection<Integer> tagIds) {
        return tagRepository.findAllByIdIn(tagIds);
    }

    @Override
    public Collection<Tag> findByUserId(Integer userId) {
        User user = userService.findById(userId);
        return tagRepository.findAllByUser(user);
    }

    @Override
    public Tag createTag(Integer userId, Tag tag) {
        User user = userService.findById(userId);
        tag.setUser(user);
        tag.setCreationTimestamp(LocalDateTime.now());
        return tagRepository.save(tag);
    }
}
