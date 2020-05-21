package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.Note;
import org.springframework.data.repository.CrudRepository;


public interface NoteRepository extends CrudRepository<Note, Integer> {
}
