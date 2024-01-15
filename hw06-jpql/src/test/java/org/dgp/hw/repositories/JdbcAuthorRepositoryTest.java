package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами ")
@JdbcTest
@Import({JpaAuthorRepository.class})
class JdbcAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository repository;

    private List<Author> dbAuthors;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
    }

    @DisplayName("должен загружать автора по id")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldReturnCorrectBookById(Author expectedAuthor) {
        var actualBook = repository.findById(expectedAuthor.getId());

        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен загружать всех авторов")
    void shouldLoadAllAuthors() {
        var actualAuthors = repository.findAll();

        assertThat(actualAuthors).containsExactlyElementsOf(dbAuthors);
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Author(id, "Author_" + id))
                .toList();
    }
}
