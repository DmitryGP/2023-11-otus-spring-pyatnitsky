package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    Mono<Book> findById(Long id);


}
