package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
