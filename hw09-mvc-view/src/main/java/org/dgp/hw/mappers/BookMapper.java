package org.dgp.hw.mappers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Genre;
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

    public Book toModel(BookCreateDto bookDto, Author author, Genre genre) {
        return new Book(0,
                bookDto.getTitle(),
                author,
                genre);
    }

    public Book toModel(BookUpdateDto bookDto, Author author, Genre genre) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                author,
                genre);
    }

    public BookUpdateDto toDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(),
                bookDto.getTitle(),
                new AuthorDto(bookDto.getAuthor().getId(), bookDto.getAuthor().getFullName()),
                new GenreDto(bookDto.getGenre().getId(), bookDto.getGenre().getName()));
    }
}
