package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

    Optional<Genre> findById(String id);

    List<Genre> findAll();
}
