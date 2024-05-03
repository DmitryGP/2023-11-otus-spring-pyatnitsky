package org.dgp.hw.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import org.dgp.hw.dto.CommentDto;
import org.dgp.hw.exceptions.NotFoundException;
import org.dgp.hw.mappers.CommentMapper;
import org.dgp.hw.models.Comment;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.CommentRepository;
import org.dgp.hw.utils.FallbackDataFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    @Override
    @HystrixCommand(commandKey = "getCommentsKey", fallbackMethod = "findByIdFallback")
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(commentMapper::toDto);
    }


    @Override
    @HystrixCommand(commandKey = "getCommentsKey", fallbackMethod = "findByBookIdFallback")
    public List<CommentDto> findByBookId(long id) {
        return commentRepository.findByBookId(id).stream().map(commentMapper::toDto).toList();
    }

    @Override
    @Transactional
    public CommentDto create(String text, long bookId) {
        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(bookId)));

        var comment = new Comment(0, text, book);

        var savedComment = commentRepository.save(comment);

        return commentMapper.toDto(savedComment);
    }

    public List<CommentDto> findByBookIdFallback(long id) {
        var comment = FallbackDataFactory.createComment();
        comment.setId(id);
        return List.of(comment);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String text) {
        var commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id=%d not found.".formatted(id)));

        commentToUpdate.setText(text);

        var savedComment = commentRepository.save(commentToUpdate);

        return commentMapper.toDto(savedComment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    public Optional<CommentDto> findByIdFallback(long id) {
        var comment = FallbackDataFactory.createComment();
        comment.getBook().setId(id);

        return Optional.of(comment);
    }
}
