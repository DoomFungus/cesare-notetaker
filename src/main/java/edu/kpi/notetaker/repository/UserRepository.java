package edu.kpi.notetaker.repository;

import edu.kpi.notetaker.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Integer countBy();

    Optional<User> findByUsername(String username);
}
