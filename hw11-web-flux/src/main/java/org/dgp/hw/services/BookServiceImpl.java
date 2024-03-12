package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.exceptions.NotFoundException;
import org.dgp.hw.mappers.BookMapper;
import org.dgp.hw.models.Book;
import org.dgp.hw.repositories.AuthorRepository;
import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.repositories.GenreRepository;
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

        var book = bookRepository.findById(id)
                .flatMap(b -> {
                    var author = authorRepository.findById(b.getAuthorId());
                    var genre = genreRepository.findById(b.getGenreId());

                    var result = Mono.zip(author, genre);

                    return result.map(t -> bookMapper.toDto(b, t.getT1(), t.getT2()));
                });

        return book;
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAllBooks();
    }

    @Override
    public void update(BookUpdateDto bookDto) {

        var bookToSave = bookRepository.findById(bookDto.getId())
                .map(b -> new Book(b.getId(), bookDto.getTitle(), b.getAuthorId(), b.getGenreId()))
                .blockOptional().orElseThrow(NotFoundException::new);

        bookRepository.save(bookToSave);
    }

    @Override
    public void create(BookCreateDto bookDto) {
        var bookToSave = bookMapper.toModel(bookDto);

        bookRepository.save(bookToSave);
    }

    @Override
    public void delete(long id) {
        bookRepository.deleteById(id);
    }
}
