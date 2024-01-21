package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.*;
import org.dgp.hw.exceptions.EntityNotFoundException;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.repositories.AuthorRepository;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<BookDto> findById(long id) {

        var optionalBook = bookRepository.findById(id);

        if(optionalBook.isEmpty()) {
            return Optional.empty();
        }
        var book = optionalBook.get();
        BookDto bookDto = BookDtoFactory.getBookDto(book);

        return Optional.of(bookDto);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookDtoFactory::getBookDto).toList();
    }

    @Override
    @Transactional
    public BookDto insert(String title, long authorId, long genreId) {
        return save(0, title, authorId, genreId);
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, long genreId) {
        return save(id, title, authorId, genreId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private BookDto save(long id, String title, long authorId, long genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));
        var book = new Book(id, title, author, genre);
        var savedBook = bookRepository.save(book);

        return BookDtoFactory.getBookDto(savedBook);
    }
}
