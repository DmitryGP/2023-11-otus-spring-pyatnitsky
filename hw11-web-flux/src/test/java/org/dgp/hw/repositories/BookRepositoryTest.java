package org.dgp.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Objects;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldReturnProperBook() {

        var book = bookRepository.findById(1L);

        StepVerifier
                .create(book)
                .expectNextMatches(b ->
                        b.getId() == 1
                                && Objects.equals(b.getTitle(), "BookTitle_1")
                                && b.getAuthorId() == 1
                                && b.getGenreId() == 1)
                .expectComplete()
                .verify();
    }
}
