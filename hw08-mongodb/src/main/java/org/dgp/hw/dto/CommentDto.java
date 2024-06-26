package org.dgp.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dgp.hw.models.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private String id;

    private String text;

    private BookDto book;

    public CommentDto(Comment comment) {
        this(comment.getId(), comment.getText(), new BookDto(comment.getBook()));
    }
}
