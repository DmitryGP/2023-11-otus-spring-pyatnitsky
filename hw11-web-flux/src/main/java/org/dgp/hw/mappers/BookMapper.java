package org.dgp.hw.mappers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookMapper {

    public BookDto toDto(Book book, Author author, Genre genre) {
        return new BookDto(book.getId(),
                book.getTitle(),
                author.getFullName(),
                genre.getName());
    }

    public Book toModel(BookCreateDto bookDto) {
        return new Book(0L,
                bookDto.getTitle(),
                bookDto.getAuthorId(),
                bookDto.getGenreId());
    }
}
