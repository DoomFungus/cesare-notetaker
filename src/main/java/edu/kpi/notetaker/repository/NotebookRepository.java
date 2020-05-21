package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.Notebook;
import org.springframework.data.repository.CrudRepository;


public interface NotebookRepository extends CrudRepository<Notebook, Integer> {
}
