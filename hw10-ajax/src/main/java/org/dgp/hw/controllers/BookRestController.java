package org.dgp.hw.controllers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.exceptions.NotFoundException;
import org.dgp.hw.services.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/v1/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/books/{id}")
    public BookDto getBook(@RequestParam long id) {
        return bookService.findById(id).orElseThrow(
                () -> new NotFoundException("No book with id = %s".formatted(id)));
    }
}
