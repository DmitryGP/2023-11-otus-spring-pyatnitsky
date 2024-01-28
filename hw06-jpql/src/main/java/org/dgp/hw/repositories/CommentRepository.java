package org.dgp.hw.repositories;

import org.dgp.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long id);

    Comment save(Comment comment);

    void deleteById(long id);
}
