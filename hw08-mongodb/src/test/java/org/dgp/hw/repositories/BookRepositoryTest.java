package org.dgp.hw.repositories;

import org.assertj.core.util.Strings;
import org.dgp.hw.datainitialization.TestDataInitializer;
import org.dgp.hw.events.MongoBookCascadeDeleteEventListener;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.dgp.hw.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами ")
@Import(MongoBookCascadeDeleteEventListener.class)
class BookRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final TestDataInitializer testDataInitializer = new TestDataInitializer();

    @BeforeEach
    void setup() {
        testDataInitializer.dropDb(mongoTemplate);
        testDataInitializer.initData(mongoTemplate);
    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getExpectedBooks")
    void shouldReturnCorrectBookById(String expectedBookId) {
        var expectedBook = mongoTemplate.findOne(findByIdQuery(expectedBookId), Book.class);

        var actualBook = repository.findById(expectedBookId);

        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repository.findAll();
        var expectedBooks = mongoTemplate.findAll(Book.class);

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
    }


    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var author = mongoTemplate.findOne(findByIdQuery("1"), Author.class);
        var genre = mongoTemplate.findOne(findByIdQuery("1"), Genre.class);

        var bookToSave = new Book("", "BookTitle_10500", author, genre);

        var returnedBook = repository.save(bookToSave);

        assertThat(mongoTemplate.findOne(findByIdQuery(returnedBook.getId()), Book.class))
                .usingRecursiveComparison()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {

        var bookToSave = mongoTemplate.findOne(findByIdQuery("1"), Book.class);
        var author = mongoTemplate.findOne(findByIdQuery("2"), Author.class);
        var genre = mongoTemplate.findOne(findByIdQuery("2"), Genre.class);

        bookToSave.setTitle("New Title vol.1");
        bookToSave.setAuthor(author);
        bookToSave.setGenre(genre);

        var returnedBook = repository.save(bookToSave);

        assertThat(returnedBook).isNotNull()
                .matches(book -> !Strings.isNullOrEmpty(book.getId()))
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(bookToSave);
    }

    @DisplayName("должен удалять книгу со всеми комментариями по id книги")
    @Test
    void shouldDeleteBook() {
        assertThat(mongoTemplate.exists(findByIdQuery("1"), Book.class)).isTrue();
        assertThat(mongoTemplate
                .find(new Query(Criteria.where("book").is("1")), Comment.class)
                .size()).isEqualTo(2);

        repository.deleteById("1");

        assertThat(mongoTemplate.exists(findByIdQuery("1"), Book.class)).isFalse();
        assertThat(mongoTemplate
                .find(new Query(Criteria.where("book").is("1")), Comment.class)
                .size()).isEqualTo(0);
    }

    private static Stream<Arguments> getExpectedBooks() {
        return LongStream.range(1, 4)
                .mapToObj(String::valueOf)
                .map(Arguments::of);
    }
}
