package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface AttachmentRepository extends CrudRepository<Attachment, Integer> {
}
