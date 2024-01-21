package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class, JpaAuthorRepository.class,
        JpaBookRepository.class, JpaGenreRepository.class})
public class JpaCommentsRepositoryTest {

    @Autowired
    private JpaCommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать комментарии по id книги")
    @ParameterizedTest
    @ArgumentsSource(BookIdCommentIdsArgsProvider.class)
    void shouldReturnCorrectCommentsByBookId(long bookId, List<Long> expectedCommentIds) {
        var actualComments = repository.findByBookId(bookId);
        var expectedComments = getExpectedComments(expectedCommentIds);
        assertThat(actualComments).containsExactlyElementsOf(expectedComments);
    }

    @DisplayName("должен загружать комментарии по id")
    @ParameterizedTest
    @MethodSource("getCommentIds")
    void shouldReturnCorrectCommentById(long commentId) {
        var actualComment = repository.findById(commentId);
        var expectedComment = em.find(Comment.class, commentId);

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }

    @DisplayName("должен создавать новый комментарий")
    @Test
    void shouldInsertNewComment() {
        var book = em.find(Book.class, 1);
        var newComment = new Comment(0, "Some text", book);

        var savedComment = repository.save(newComment);

        var commentFromDb = em.find(Comment.class, savedComment.getId());//repository.findById(savedComment.getId());

        assertThat(commentFromDb).isEqualTo(savedComment);

    }

    @DisplayName("должен сохрнять изменённый комментарий")
    @Test
    void shouldUpdateComment() {
        var updatedText = "updated text";
        var comment = repository.findById(1).get();

        comment.setText(updatedText);

        repository.save(comment);

        var savedComment = em.find(Comment.class, 1);

        assertThat(savedComment.getText()).isEqualTo(updatedText);

    }

    @DisplayName("должен удалять кщмментарий")
    @Test
    void shouldDeleteComment() {

        var comment = em.find(Comment.class, 1);

        assertThat(comment).isNotNull();

        var bookId = comment.getBook().getId();

        repository.deleteById(1);

        comment = em.find(Comment.class, 1);

        assertThat(comment).isNull();

        var book = em.find(Book.class, bookId);

        assertThat(book.getComments().stream().filter(c -> c.getId() == 1).count()).isEqualTo(0);
    }

    private List<Comment> getExpectedComments(List<Long> expectedCommentIds) {
        return expectedCommentIds.stream().map(id -> em.find(Comment.class, id)).toList();
    }

}
