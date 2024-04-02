package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public CommentMongo map(Comment comment) {
        return CommentMongo.builder()
                .id(String.valueOf(comment.getId()))
                .text(comment.getText())
                .bookId(String.valueOf(comment.getBook().getId()))
                .build();
    }
}
