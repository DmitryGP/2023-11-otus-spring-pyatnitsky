package org.dgp.hw.mappers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.models.Book;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;

    public BookCreateDto toCreateDto(Book book) {
        return new BookCreateDto(book.getId(),
                book.getTitle(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre()));
    }

    public BookUpdateDto toUpdateDto(Book book) {
        return new BookUpdateDto(book.getId(),
                book.getTitle(),
                authorMapper.toDto(book.getAuthor()),
                genreMapper.toDto(book.getGenre()));
    }

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

    public Book toModel(BookCreateDto bookDto) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                authorMapper.toModel(bookDto.getAuthor()),
                genreMapper.toModel(bookDto.getGenre()));
    }

    public Book toModel(BookUpdateDto bookDto) {
        return new Book(bookDto.getId(),
                bookDto.getTitle(),
                authorMapper.toModel(bookDto.getAuthor()),
                genreMapper.toModel(bookDto.getGenre()));
    }

    public BookUpdateDto toDto(BookDto bookDto) {
        return new BookUpdateDto(bookDto.getId(),
                bookDto.getTitle(),
                new AuthorDto(bookDto.getAuthor().getId(), bookDto.getAuthor().getFullName()),
                new GenreDto(bookDto.getGenre().getId(), bookDto.getGenre().getName()));
    }
}
