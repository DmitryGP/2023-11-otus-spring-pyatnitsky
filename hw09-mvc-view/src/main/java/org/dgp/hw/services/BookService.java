package org.dgp.hw.services;

import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();

    BookDto create(BookCreateDto bookDto);

    BookDto update(BookUpdateDto bookDto);

    void deleteById(long id);
}
