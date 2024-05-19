package org.dgp.hw.commands;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.converters.CommentConverter;
import org.dgp.hw.services.CommentService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by book id", key = "cbbid")
    public String findCommentByBookId(String id) {

        return commentService.findByBookId(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById(String id) {

        return commentService.findById(id).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertComment(String text, String bookId) {
        var newComment = commentService.create(text, bookId);

        return commentConverter.commentToString(newComment);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, String text) {
        var updatedComment = commentService.update(id, text);

        return commentConverter.commentToString(updatedComment);
    }

    @ShellMethod(value = "Delete comment", key = "cdel")
    public void deleteComment(String id) {
        commentService.deleteById(id);
    }
}
