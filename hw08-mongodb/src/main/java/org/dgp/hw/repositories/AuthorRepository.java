package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findById(String id);

    List<Author> findAll();
}
