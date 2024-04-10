package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.models.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public BookMongo map(Book book) {
        return BookMongo.builder()
                .id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .author(authorMapper.map(book.getAuthor()))
                .genre(genreMapper.map(book.getGenre()))
                .build();
    }
}
