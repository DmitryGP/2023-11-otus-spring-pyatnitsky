package org.dgp.hw.datamigration.service;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.mappers.CommentMapper;
import org.dgp.hw.datamigration.models.BookTemp;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.CommentTemp;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMongoItemProcessor implements ItemProcessor<CommentTemp, CommentMongo> {

    private final MongoOperations mongoOperations;

    private final CommentMapper commentMapper;

    @Override
    public CommentMongo process(CommentTemp item) {

        String bookId = "";

        if (item.getBookId() != 0) {
            var findBook = new Query(Criteria.where("id").is(String.valueOf(item.getBookId())));
            var book = mongoOperations.findOne(findBook, BookTemp.class);
            bookId = book.getMongoId();
        }

        return commentMapper.map(item, bookId);
    }
}
