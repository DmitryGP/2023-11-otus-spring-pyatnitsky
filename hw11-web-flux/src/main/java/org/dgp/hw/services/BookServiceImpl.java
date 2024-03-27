package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.mappers.BookMapper;
import org.dgp.hw.models.Book;
import org.dgp.hw.repositories.AuthorRepository;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.GenreRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookMapper bookMapper;

    @Override
    public Mono<BookDto> findById(long id) {

        return bookRepository.findById(id)
                .flatMap(this::getMonoBookDto);
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAllBooks();
    }

    @Override
    public Mono<BookDto> update(BookUpdateDto bookDto) {

        return bookRepository.findById(bookDto.getId())
                .map(b -> new Book(b.getId(), bookDto.getTitle(), b.getAuthorId(), b.getGenreId()))
                .flatMap(bookRepository::save)
                .flatMap(this::getMonoBookDto);
    }

    @Override
    public Mono<BookDto> create(BookCreateDto bookDto) {
        var bookToSave = bookMapper.toModel(bookDto);

        return bookRepository.save(bookToSave).flatMap(this::getMonoBookDto);

    }

    @Override
    public Mono<Void> delete(long id) {
        return bookRepository.deleteById(id);
    }

    @NotNull
    private Mono<BookDto> getMonoBookDto(Book b) {
        var author = authorRepository.findById(b.getAuthorId());
        var genre = genreRepository.findById(b.getGenreId());

        var result = Mono.zip(author, genre);

        return result.map(t -> bookMapper.toDto(b, t.getT1(), t.getT2()));
    }
}
