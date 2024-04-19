package org.dgp.hw.repositories;

import org.dgp.hw.changelogs.TestDataInitializer;
import org.dgp.hw.models.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами ")
class AuthorRepositoryTest extends AbstractRepositoryTest
{

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final TestDataInitializer testDataInitializer = new TestDataInitializer();

    @BeforeEach
    void setup() {
        testDataInitializer.dropDb(mongoTemplate);
        testDataInitializer.initData(mongoTemplate);
    }


    @DisplayName("должен загружать автора по id")
    @ParameterizedTest
    @MethodSource("getDbAuthors")
    void shouldReturnCorrectAuthorById(String authorId) {

        var actualAuthor = repository.findById(authorId);

        var expectedAuthor = mongoTemplate.find(new Query(Criteria.where("id").is(authorId)), Author.class);

        assertThat(actualAuthor).isPresent()
                .get()
                .isEqualTo(expectedAuthor.get(0));
    }

    @Test
    @DisplayName("должен загружать всех авторов")
    void shouldLoadAllAuthors() {
        var actualAuthors = repository.findAll();

        var allAuthors = mongoTemplate.findAll(Author.class);

        assertThat(actualAuthors).containsExactlyElementsOf(allAuthors);
    }

    public static Stream<Arguments> getDbAuthors() {
        return LongStream.range(1, 4)
                .mapToObj(String::valueOf)
                .map(Arguments::of);
    }
}
