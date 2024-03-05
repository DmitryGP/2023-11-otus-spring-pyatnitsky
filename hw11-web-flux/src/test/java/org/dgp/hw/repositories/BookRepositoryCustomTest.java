package org.dgp.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringBootTest
public class BookRepositoryCustomTest {

    @Autowired
    private BookRepositoryCustom bookRepository;

    @Test
    void shouldReturnAllBooks() {
        var books = bookRepository.findAll();

        StepVerifier
                .create(books)
                .expectNextMatches(b ->
                        b.getId() == 1
                        && Objects.equals(b.getTitle(), "BookTitle_1")
                        && Objects.equals(b.getAuthor(), "Author_1")
                        && Objects.equals(b.getGenre(), "Genre_1"))
                .expectNextMatches(b ->
                        b.getId() == 2
                        && Objects.equals(b.getTitle(), "BookTitle_2")
                        && Objects.equals(b.getAuthor(), "Author_2")
                        && Objects.equals(b.getGenre(), "Genre_2"))
                .expectNextMatches(b ->
                        b.getId() == 3
                        && Objects.equals(b.getTitle(), "BookTitle_3")
                        && Objects.equals(b.getAuthor(), "Author_3")
                        && Objects.equals(b.getGenre(), "Genre_3"))
                .expectComplete()
                .verify();

    }
}
