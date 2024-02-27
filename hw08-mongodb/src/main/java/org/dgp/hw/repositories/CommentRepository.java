package org.dgp.hw.repositories;

import org.dgp.hw.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, Long> {

    List<Comment> findByBookId(long id);

    Optional<Comment> findById(long id);

    void deleteById(long id);


}
