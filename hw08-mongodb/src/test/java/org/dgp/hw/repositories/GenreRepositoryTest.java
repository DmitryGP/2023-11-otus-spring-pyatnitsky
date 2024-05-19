package org.dgp.hw.repositories;

import org.dgp.hw.datainitialization.TestDataInitializer;
import org.dgp.hw.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами ")
public class GenreRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final TestDataInitializer testDataInitializer = new TestDataInitializer();

    @BeforeEach
    void setup() {
        testDataInitializer.dropDb(mongoTemplate);
        testDataInitializer.initData(mongoTemplate);
    }

    @DisplayName("должен загружать жанр по id")
    @ParameterizedTest
    @MethodSource("getDbGenres")
    void shouldReturnCorrectGenreById(String genreId) {
        var actualGenre = repository.findById(genreId);

        var expectedGenre = mongoTemplate.findOne(findByIdQuery(genreId), Genre.class);

        assertThat(actualGenre).isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен загружать все жанры")
    void shouldLoadAllGenres() {
        var actualGenres = repository.findAll();

        var allGenres = LongStream.range(1, 4)
                .mapToObj(String::valueOf)
                .map(i -> mongoTemplate.findOne(findByIdQuery(i), Genre.class))
                .toList();

        assertThat(actualGenres)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(allGenres);
    }

    public static Stream<Arguments> getDbGenres() {
        return LongStream.range(1, 4)
                .mapToObj(String::valueOf)
                .map(Arguments::of);
    }
}
