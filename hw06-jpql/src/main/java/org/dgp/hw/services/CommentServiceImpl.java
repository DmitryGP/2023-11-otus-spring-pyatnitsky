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
    @Transactional
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(CommentDto::new);
    }


    @Override
    @Transactional
    public List<CommentDto> findByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(CommentDto::new).toList();
    }

    @Override
    @Transactional
    public CommentDto create(String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(0, text, book);

        var savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text, long bookId) {
        var commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment with id=%d not found.".formatted(id)));

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id=%d not found".formatted(bookId)));

        commentToUpdate.setText(text);
        commentToUpdate.setBook(book);

        var savedComment = commentRepository.save(commentToUpdate);

        return new CommentDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }
}
