package org.dgp.hw.datamigration.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.mappers.BookMapper;
import org.dgp.hw.datamigration.models.AuthorTemp;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.datamigration.models.BookTemp;
import org.dgp.hw.datamigration.models.GenreTemp;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMongoItemProcessor implements ItemProcessor<BookTemp, BookMongo> {

    private final MongoOperations mongoOperations;

    private final BookMapper bookMapper;

    @Override
    public BookMongo process(BookTemp item) {

        var findAuthor = new Query(Criteria.where("id").is(String.valueOf(item.getAuthorId())));
        var findGenre = new Query(Criteria.where("id").is(String.valueOf(item.getGenreId())));

        var author = mongoOperations.findOne(findAuthor, AuthorTemp.class);
        var genre = mongoOperations.findOne(findGenre, GenreTemp.class);

        return bookMapper.map(item, author.getMongoId(), genre.getMongoId());
    }
}
