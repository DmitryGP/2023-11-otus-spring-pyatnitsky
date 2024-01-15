package org.dgp.hw.services;

import lombok.AllArgsConstructor;
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
public class CommentServiceImpl implements CommentService{

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;
    @Override
    @Transactional
    public Optional<Comment> findById(long id) {

        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Comment> findByBookId(long id) {
        return commentRepository.findByBookId(id);
    }

    @Override
    @Transactional
    public Comment insert(String text, long bookId) {

        return save(0L, text, bookId);
    }

    @Override
    @Transactional
    public Comment update(long id, String text, long bookId) {
        return save(id, text, bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(id, text, book);

        return commentRepository.save(comment);
    }
}
