package org.dgp.hw.services;

import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<BookDto> findById(long id);

    Flux<BookDto> findAll();

    Mono<BookDto> update(BookUpdateDto bookDto);

    Mono<BookDto> create(BookCreateDto bookDto);

    Mono<Void> delete(long id);
}
