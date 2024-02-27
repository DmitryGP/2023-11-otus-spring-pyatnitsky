package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(long id);

    List<Author> findAll();
}
