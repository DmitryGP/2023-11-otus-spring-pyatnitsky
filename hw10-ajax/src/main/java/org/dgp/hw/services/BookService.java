package org.dgp.hw.services;

import org.dgp.hw.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto findById(long id);

    List<BookDto> findAll();
}
