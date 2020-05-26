package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.Notebook;
import edu.kpi.notetaker.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface NotebookRepository extends CrudRepository<Notebook, Integer> {
    Collection<Notebook> findAllByUser(User user);
}
