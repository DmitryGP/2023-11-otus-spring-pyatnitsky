package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findById(String id);

    List<Author> findAll();
}
