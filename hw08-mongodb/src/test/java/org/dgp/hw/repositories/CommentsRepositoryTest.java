package org.dgp.hw.repositories;

import org.dgp.hw.changelogs.TestDataInitializer;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями ")
public class CommentsRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final TestDataInitializer testDataInitializer = new TestDataInitializer();

    @BeforeEach
    void setup() {
        testDataInitializer.dropDb(mongoTemplate);
        testDataInitializer.initData(mongoTemplate);
    }

    @DisplayName("должен загружать комментарии по id книги")
    @ParameterizedTest
    @MethodSource("getBookIdCommentIds")
    void shouldReturnCorrectCommentsByBookId(String bookId, List<String> expectedCommentIds) {
        var actualComments = repository.findByBookId(bookId);
        var expectedComments = getExpectedComments(expectedCommentIds);

        assertThat(actualComments)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedComments);
    }

    @DisplayName("должен загружать комментарии по id")
    @ParameterizedTest
    @MethodSource("getCommentIds")
    void shouldReturnCorrectCommentById(String commentId) {
        var actualComment = repository.findById(commentId);
        var expectedComment = mongoTemplate.findOne(findByIdQuery(commentId), Comment.class);

        assertThat(actualComment).isPresent().get()
                .usingRecursiveComparison()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен создавать новый комментарий")
    @Test
    void shouldInsertNewComment() {
        var book = mongoTemplate.findOne(findByIdQuery("1"), Book.class);
        var newComment = new Comment("", "Some text", book);

        var savedComment = repository.save(newComment);

        var commentFromDb = mongoTemplate.findOne(findByIdQuery(savedComment.getId()), Comment.class);

        assertThat(commentFromDb)
                .usingRecursiveComparison()
                .isEqualTo(savedComment);

    }

    @DisplayName("должен сохрнять изменённый комментарий")
    @Test
    void shouldUpdateComment() {
        var updatedText = "updated text";
        var comment = repository.findById("1").get();

        comment.setText(updatedText);

        repository.save(comment);

        var savedComment = mongoTemplate.findOne(findByIdQuery("1"), Comment.class);

        assertThat(savedComment.getText()).isEqualTo(updatedText);

    }

    @DisplayName("должен удалять комментарий")
    @Test
    void shouldDeleteComment() {

        var comment = mongoTemplate.findOne(findByIdQuery("1"), Comment.class);

        assertThat(comment).isNotNull();

        var bookId = comment.getBook().getId();

        repository.deleteById(bookId);

        comment = mongoTemplate.findOne(findByIdQuery(bookId), Comment.class);

        assertThat(comment).isNull();
    }

    public static Stream<Arguments> getCommentIds() {
        return IntStream.range(1, 6).mapToObj(String::valueOf).map(Arguments::of);
    }

    public static Stream<Arguments> getBookIdCommentIds() {
        return Stream.of(Arguments.of("1", new ArrayList<>(List.of("1", "4"))),
                Arguments.of("2", new ArrayList<>(List.of("2"))),
                Arguments.of("3", new ArrayList<>(List.of("3", "5"))));
    }

    private List<Comment> getExpectedComments(List<String> expectedCommentIds) {
        return expectedCommentIds.stream().map(id -> mongoTemplate.findOne(findByIdQuery(id), Comment.class)).toList();
    }

}
