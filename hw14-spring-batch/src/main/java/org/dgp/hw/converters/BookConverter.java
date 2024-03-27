package org.dgp.hw.converters;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.dto.BookDto;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(BookDto book) {
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre()));
    }

    public String mongoBookToString(BookMongo book) {
        return "Id: %s, Title: %s, Author Id: %s, Genre Id: %s".formatted(
                book.getId(),
                book.getTitle(),
                book.getAuthorId(),
                book.getGenreId());
    }
}
