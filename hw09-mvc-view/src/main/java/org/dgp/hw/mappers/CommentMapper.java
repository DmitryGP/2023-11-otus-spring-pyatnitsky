package org.dgp.hw.mappers;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.CommentDto;
import org.dgp.hw.models.Comment;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final BookMapper bookMapper;
    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), bookMapper.toDto(comment.getBook()));
    }
}
