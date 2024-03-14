package org.dgp.hw.controllers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Mono<ResponseEntity<BookDto>> getBook(@PathVariable long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/api/v1/books")
    public
    Mono<ResponseEntity<BookDto>> createBook(@RequestBody BookCreateDto bookDto) {
        return bookService.create(bookDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/api/v1/books")
    public Mono<ResponseEntity<BookDto>> updateBook(@RequestBody BookUpdateDto bookDto) {
        return bookService.update(bookDto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/api/v1/books/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable long id) {
        return bookService.delete(id)
                .map(ResponseEntity::ok);
    }

}
