package org.dgp.hw.converters;

import lombok.AllArgsConstructor;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.dto.CommentDto;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentConverter {

    public String commentToString(CommentDto comment) {
        return "Id: %d, text %s"
                .formatted(comment.getId(),
                        comment.getText());
    }

    public String mongoCommentToString(CommentMongo comment) {
        return "Id: %s, Text: %s, Book Id: %s".formatted(
                comment.getId(),
                comment.getText(),
                comment.getBookId());
    }
}
