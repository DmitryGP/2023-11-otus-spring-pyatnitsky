package org.dgp.hw.converters;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.CommentDto;
import org.dgp.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentConverter {

    public String commentToString(CommentDto comment) {
        return "Id: %d, text %s"
                .formatted(comment.getId(),
                        comment.getText());
    }
}
