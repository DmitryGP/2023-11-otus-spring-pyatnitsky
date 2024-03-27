package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
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

@DisplayName("Репозиторий на основе Jpa для работы с жанрами ")
@DataJpaTest
public class JpaGenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать жанр по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldReturnCorrectGenreById(Long genreId) {
        var actualGenre = repository.findById(genreId);

        var expectedGenre = em.find(Genre.class, genreId);

        assertThat(actualGenre).isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен загружать все жанры")
    void shouldLoadAllGenres() {
        var actualGenres = repository.findAll();

        var allGenres = LongStream.range(1, 4).mapToObj(i -> em.find(Genre.class, i)).toList();

        assertThat(actualGenres).containsExactlyElementsOf(allGenres);
    }

    public static Stream<Arguments> getDbGenres() {
        return LongStream.range(1, 4).mapToObj(Arguments::of);
    }
}
