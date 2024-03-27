package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
public class JpaCommentsRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать комментарии по id книги")
    @ParameterizedTest
    @MethodSource("getBookIdCommentIds")
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

        var commentFromDb = em.find(Comment.class, savedComment.getId());

        assertThat(commentFromDb).isEqualTo(savedComment);

    }

    @DisplayName("должен сохрнять изменённый комментарий")
    @Test
    void shouldUpdateComment() {
        var updatedText = "updated text";
        var comment = repository.findById(1L).get();

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

        repository.deleteById(1L);

        comment = em.find(Comment.class, 1);

        assertThat(comment).isNull();
    }

    public static Stream<Arguments> getCommentIds() {
        return IntStream.range(1, 7).mapToObj(Arguments::of);
    }

    public static Stream<Arguments> getBookIdCommentIds() {
        return Stream.of(Arguments.of(1L, new ArrayList<>(List.of(3L, 6L))),
                Arguments.of(2L, new ArrayList<>(List.of(1L, 4L))),
                Arguments.of(3L, new ArrayList<>(List.of(2L, 5L))));
    }

    private List<Comment> getExpectedComments(List<Long> expectedCommentIds) {
        return expectedCommentIds.stream().map(id -> em.find(Comment.class, id)).toList();
    }

}
