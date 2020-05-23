package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.Note;
import edu.kpi.notetaker.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;


public interface NoteRepository extends CrudRepository<Note, Integer> {
    @Query("SELECT note " +
            "from Note note join note.tags tag " +
            "where tag in :tags " +
            "group by note " +
            "having count(tag) = :tagsCount")
    Collection<Note> findByTagsIn(Collection<Tag> tags, Long tagsCount);
}
