package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с авторами ")
@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать автора по id")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldReturnCorrectAuthorById(Long authorId) {

        var actualAuthor = repository.findById(authorId);

        var expectedAuthor = em.find(Author.class, authorId);

        assertThat(actualAuthor).isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен загружать всех авторов")
    void shouldLoadAllAuthors() {
        var actualAuthors = repository.findAll();

        var allAuthors = LongStream.range(1, 4).mapToObj(i -> em.find(Author.class, i)).toList();

        assertThat(actualAuthors).containsExactlyElementsOf(allAuthors);
    }

    public static Stream<Arguments> getDbAuthors() {
        return LongStream.range(1, 4).mapToObj(Arguments::of);
    }
}
