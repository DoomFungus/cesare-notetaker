package edu.kpi.notetaker.service;

import edu.kpi.notetaker.model.Tag;

import java.util.Collection;

public interface TagService {
    Tag findById(Integer tagId);

    Collection<Tag> findByIds(Collection<Integer> tagIds);

    Collection<Tag> findByUserId(Integer userId);

    Tag createTag(Integer userId, Tag tag);
}
