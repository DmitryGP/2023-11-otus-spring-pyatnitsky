package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.exceptions.EntityNotFoundException;
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
    @Transactional(readOnly = true)
    public Optional<BookDto> findById(String id) {

        var book = bookRepository.findById(id);

        if (book.isEmpty()) {
            return Optional.empty();
        }

        return book.map(BookDto::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookDto::new).toList();
    }

    @Override
    @Transactional
    public BookDto create(String title, String authorId, String genreId) {
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));

        var book = new Book("", title, author, genre);

        var savedBook = bookRepository.save(book);

        return new BookDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(String id, String title, String authorId, String genreId) {

        var bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id=%d not found".formatted(id)));
        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new EntityNotFoundException("Genre with id %d not found".formatted(genreId)));

        bookToUpdate.setTitle(title);
        bookToUpdate.setAuthor(author);
        bookToUpdate.setGenre(genre);

        var savedBook = bookRepository.save(bookToUpdate);

        return new BookDto(savedBook);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        bookRepository.deleteById(id);
    }
}
