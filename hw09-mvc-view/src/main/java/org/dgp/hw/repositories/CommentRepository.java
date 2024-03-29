package org.dgp.hw.repositories;

import org.dgp.hw.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();

    List<Comment> findByBookId(long id);


}
