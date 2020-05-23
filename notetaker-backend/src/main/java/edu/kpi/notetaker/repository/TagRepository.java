package edu.kpi.notetaker.repository;


import edu.kpi.notetaker.model.Tag;
import edu.kpi.notetaker.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Collection<Tag> findAllByIdIn(Collection<Integer> ids);

    Collection<Tag> findAllByUser(User user);
}
