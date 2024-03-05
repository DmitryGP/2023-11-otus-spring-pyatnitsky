package org.dgp.hw.controllers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.services.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public Flux<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{id}")
    public Mono<BookDto> getBook(@PathVariable long id) {
        return bookService.findById(id);
    }
}
