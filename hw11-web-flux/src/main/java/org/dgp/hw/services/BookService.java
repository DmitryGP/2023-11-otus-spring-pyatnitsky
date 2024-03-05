package org.dgp.hw.services;

import org.dgp.hw.dto.BookDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<BookDto> findById(long id);

    Flux<BookDto> findAll();
}
