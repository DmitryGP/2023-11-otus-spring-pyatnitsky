package org.dgp.hw.mappers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.*;
import org.dgp.hw.models.Book;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    public BookDto toDto(Book book) {
        return new BookDto(book.getId(),
                book.getTitle(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre()));
    }

    public Book toModel(BookDto bookDto) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                authorMapper.toModel(bookDto.getAuthor()),
                genreMapper.toModel(bookDto.getGenre()));
    }
}
