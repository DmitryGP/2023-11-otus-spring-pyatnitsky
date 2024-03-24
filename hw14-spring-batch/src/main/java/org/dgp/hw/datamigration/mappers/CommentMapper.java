package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.CommentTemp;
import org.dgp.hw.datamigration.service.MongoIdGenerator;
import org.dgp.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final MongoIdGenerator mongoIdGenerator;

    public CommentTemp map(Comment comment) {
        return CommentTemp.builder()
                .id(String.valueOf(comment.getId()))
                .text(comment.getText())
                .mongoId(mongoIdGenerator.generateId())
                .build();
    }

    public CommentMongo map(CommentTemp comment, String bookId) {
        return CommentMongo.builder()
                .id(comment.getMongoId())
                .text(comment.getText())
                .bookId(bookId)
                .build();
    }
}
