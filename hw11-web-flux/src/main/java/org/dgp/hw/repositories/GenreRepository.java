package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {

}
