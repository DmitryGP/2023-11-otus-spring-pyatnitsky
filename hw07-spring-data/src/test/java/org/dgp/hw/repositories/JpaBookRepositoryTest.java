package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@DataJpaTest
class JpaBookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private TestEntityManager em;

    private static Stream<Arguments> getExpectedBooks() {
        return LongStream.range(1, 4).mapToObj(Arguments::of);
    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getExpectedBooks")
    void shouldReturnCorrectBookById(Long expectedBookId) {
        var expectedBook = em.find(Book.class, expectedBookId);
        var actualBook = repository.findById(expectedBookId);
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repository.findAll();
        var expectedBooks = getAllBooks();

        assertThat(actualBooks).containsExactlyElementsOf(expectedBooks);
        actualBooks.forEach(System.out::println);
    }

    private List<Book> getAllBooks() {
        return IntStream.range(1, 4).mapToObj(i -> em.find(Book.class, i)).toList();
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var author = em.find(Author.class, 1);
        var genre = em.find(Genre.class, 1);
        var bookToSave = new Book(0, "BookTitle_10500", author, genre);

        var returnedBook = repository.save(bookToSave);

        assertThat(em.find(Book.class, returnedBook.getId()))
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {

        var bookToSave = em.find(Book.class, 1);
        var author = em.find(Author.class, 2);
        var genre = em.find(Genre.class, 2);

        bookToSave.setTitle("New Title vol.1");
        bookToSave.setAuthor(author);
        bookToSave.setGenre(genre);

        var returnedBook = repository.save(bookToSave);

        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(bookToSave);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(em.find(Book.class, 1)).isNotNull();
        repository.deleteById(1L);
        assertThat(em.find(Book.class, 1)).isNull();
    }
}