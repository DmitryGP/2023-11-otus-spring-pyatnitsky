package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {

}
