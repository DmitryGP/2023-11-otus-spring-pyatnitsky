package org.dgp.hw.services;

import lombok.AllArgsConstructor;
import org.dgp.hw.dto.CommentDto;
import org.dgp.hw.exceptions.EntityNotFoundException;
import org.dgp.hw.models.Comment;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> findById(String id) {
        return commentRepository.findById(id).map(CommentDto::new);
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findByBookId(String id) {
        var c = commentRepository.findByBookId(id);

        return c.stream().map(CommentDto::new).toList();
    }

    @Override
    @Transactional
    public CommentDto create(String text, String bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment("", text, book);

        var savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(String id, String text) {
        var commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id=%d not found.".formatted(id)));

        commentToUpdate.setText(text);

        var savedComment = commentRepository.save(commentToUpdate);

        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.deleteById(id);
    }
}
