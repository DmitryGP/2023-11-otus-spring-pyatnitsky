package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {

}
