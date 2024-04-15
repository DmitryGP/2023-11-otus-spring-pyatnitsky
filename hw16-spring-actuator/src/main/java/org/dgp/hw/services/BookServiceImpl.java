package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.exceptions.NotFoundException;
import org.dgp.hw.mappers.BookMapper;
import org.dgp.hw.repositories.AuthorRepository;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public BookDto findById(long id) {

        var book = bookRepository.findById(id);

        return book.map(bookMapper::toDto).orElseThrow(() ->
                new NotFoundException("Book with id = %s is not found".formatted(id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }

    @Override
    @Transactional
    public BookDto create(BookCreateDto bookDto) {
        var author = authorRepository.findById(bookDto.getAuthor().getId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found"
                        .formatted(bookDto.getAuthor().getId())));
        var genre = genreRepository.findById(bookDto.getGenre().getId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found"
                        .formatted(bookDto.getGenre().getId())));

        var book = bookMapper.toModel(bookDto, author, genre);

        var savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto update(BookUpdateDto bookDto) {

        var author = authorRepository.findById(bookDto.getAuthor().getId())
                .orElseThrow(() -> new NotFoundException("Author with id %d not found"
                        .formatted(bookDto.getAuthor().getId())));
        var genre = genreRepository.findById(bookDto.getGenre().getId())
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found"
                        .formatted(bookDto.getGenre().getId())));

        var bookToUpdate = bookMapper.toModel(bookDto, author, genre);

        var savedBook = bookRepository.save(bookToUpdate);

        return bookMapper.toDto(savedBook);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public long booksCount() {
        return bookRepository.count();
    }
}
