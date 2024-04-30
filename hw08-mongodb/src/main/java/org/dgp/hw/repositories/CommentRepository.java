package org.dgp.hw.repositories;

import org.dgp.hw.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByBookId(String id);

    Optional<Comment> findById(String id);

    void deleteById(String id);

    void deleteByBookId(String bookId);


}
