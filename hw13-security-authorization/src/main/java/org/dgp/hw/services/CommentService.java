package org.dgp.hw.services;

import org.dgp.hw.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentDto> findById(long id);

    List<CommentDto> findByBookId(long id);

    CommentDto create(String text, long bookId);

    CommentDto update(long id, String text);

    void deleteById(long id);
}
