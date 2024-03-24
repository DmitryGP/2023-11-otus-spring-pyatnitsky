package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.datamigration.models.BookTemp;
import org.dgp.hw.datamigration.service.MongoIdGenerator;
import org.dgp.hw.models.Book;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final MongoIdGenerator mongoIdGenerator;

    public BookTemp map(Book book) {
        return BookTemp.builder()
                .Id(String.valueOf(book.getId()))
                .title(book.getTitle())
                .mongoId(mongoIdGenerator.generateId())
                .authorId(book.getAuthor().getId())
                .genreId(book.getGenre().getId())
                .build();
    }


    public BookMongo map(BookTemp bookTemp, String authorId, String genreId) {
        return BookMongo.builder()
                .Id(bookTemp.getMongoId())
                .title(bookTemp.getTitle())
                .authorId(authorId)
                .genreId(genreId)
                .build();
    }
}
