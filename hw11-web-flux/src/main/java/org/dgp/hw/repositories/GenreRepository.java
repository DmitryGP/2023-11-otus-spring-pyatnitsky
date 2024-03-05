package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface GenreRepository extends ReactiveCrudRepository<Genre, Long> {

    Mono<Genre> findById(long id);
}
