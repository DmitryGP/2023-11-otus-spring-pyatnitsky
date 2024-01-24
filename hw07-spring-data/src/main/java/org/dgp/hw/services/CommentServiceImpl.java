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

        var optionalComment = commentRepository.findById(id);

        if (optionalComment.isEmpty()) {
            return Optional.empty();
        }

        var comment = optionalComment.get();

        var commentDto = new CommentDto(comment);

        return Optional.of(commentDto);
    }


    @Override
    @Transactional
    public List<CommentDto> findByBookId(long id) {
        var comments = commentRepository.findByBookId(id);

        return comments.stream().map(CommentDto::new).toList();
    }

    @Override
    @Transactional
    public CommentDto insert(String text, long bookId) {
        return save(0L, text, bookId);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text, long bookId) {
        return save(id, text, bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private CommentDto save(long id, String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(id, text, book);

        var savedComment = commentRepository.save(comment);

        return new CommentDto(savedComment);
    }
}