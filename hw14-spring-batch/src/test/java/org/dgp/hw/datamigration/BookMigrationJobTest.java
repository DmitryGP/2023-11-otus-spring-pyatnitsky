package org.dgp.hw.datamigration;

import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.dgp.hw.models.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dgp.hw.datamigration.config.JobConfig.IMPORT_BOOKS_JOB_NAME;

@SpringBootTest
@SpringBatchTest
public class BookMigrationJobTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    private MongoOperations mongoOperations;

    private List<Book> expectedBooks;

    private List<Comment> expectedComments;

    @BeforeEach
    void clearMetaData() {
        jobRepositoryTestUtils.removeJobExecutions();

        mongoOperations.dropCollection(BookMongo.class);
        mongoOperations.dropCollection(AuthorMongo.class);
        mongoOperations.dropCollection(GenreMongo.class);
        mongoOperations.dropCollection(CommentMongo.class);
    }

    @Test
    void testJob() throws Exception {
        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_BOOKS_JOB_NAME);

        var jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        var authors = mongoOperations.findAll(AuthorMongo.class);

        assertThat(authors).hasSize(3);

        var genres = mongoOperations.findAll(GenreMongo.class);

        assertThat(genres).hasSize(3);

        var books = mongoOperations.findAll(BookMongo.class);

        assertThat(books).hasSize(3);

        var comments = mongoOperations.findAll(CommentMongo.class);

        assertThat(comments).hasSize(6);
    }

    @Test
    void dataIntegrityTest() throws Exception {

        initializeExpectedValues();

        Job job = jobLauncherTestUtils.getJob();
        assertThat(job).isNotNull()
                .extracting(Job::getName)
                .isEqualTo(IMPORT_BOOKS_JOB_NAME);

        var jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        expectedBooks.forEach(b -> {

            var comments = expectedComments.stream()
                    .filter(c -> c.getBook().getId() == b.getId())
                    .toList();

            checkData(b, comments);
        });
    }

    private void checkData(Book book, List<Comment> comments) {

        var findBook = new Query(Criteria.where("title").is(book.getTitle()));

        var bookMongo = mongoOperations.findOne(findBook, BookMongo.class);

        assertThat(bookMongo).isNotNull();

        var findAuthor = new Query(Criteria.where("id").is(bookMongo.getAuthor().getId()));

        var authorMongo = mongoOperations.findOne(findAuthor, AuthorMongo.class);

        assertThat(authorMongo).isNotNull();
        assertThat(authorMongo.getFullName()).isEqualTo(book.getAuthor().getFullName());

        var findGenre = new Query(Criteria.where("id").is(bookMongo.getGenre().getId()));

        var genreMongo = mongoOperations.findOne(findGenre, GenreMongo.class);

        assertThat(genreMongo).isNotNull();
        assertThat(genreMongo.getName()).isEqualTo(book.getGenre().getName());

        var findComments = new Query(Criteria.where("bookId").is(bookMongo.getId()));

        var commentsMongo = mongoOperations.find(findComments, CommentMongo.class);

        var actualCommentTexts = commentsMongo
                .stream()
                .map(CommentMongo::getText).toList();

        var expectedCommentTexts = comments
                .stream().
                map(Comment::getText).toList();

        assertThat(actualCommentTexts).containsExactlyElementsOf(expectedCommentTexts);
    }

    private void initializeExpectedValues() {
        expectedBooks = List.of(Book.builder()
                .id(1L)
                .title("BookTitle_1")
                .author(Author.builder()
                        .id(1)
                        .fullName("Author_1")
                        .build())
                .genre(Genre.builder()
                        .id(1)
                        .name("Genre_1")
                        .build())
                .build(),
                Book.builder()
                        .id(2)
                        .title("BookTitle_2")
                        .author(Author.builder()
                                .id(2)
                                .fullName("Author_2")
                                .build())
                        .genre(Genre.builder()
                                .id(2)
                                .name("Genre_2")
                                .build())
                        .build(),
                Book.builder()
                        .id(3)
                        .title("BookTitle_3")
                        .author(Author.builder()
                                .id(3)
                                .fullName("Author_3")
                                .build())
                        .genre(Genre.builder()
                                .id(3)
                                .name("Genre_3")
                                .build())
                        .build());

        expectedComments = List.of(Comment.builder()
                .id(1)
                .text("CommentText_1")
                .book(expectedBooks.get(1))
                .build(),
                Comment.builder()
                        .id(2)
                        .text("CommentText_2")
                        .book(expectedBooks.get(2))
                        .build(),
                Comment.builder()
                        .id(3)
                        .text("CommentText_3")
                        .book(expectedBooks.get(0))
                        .build(),
                Comment.builder()
                        .id(4)
                        .text("CommentText_4")
                        .book(expectedBooks.get(1))
                        .build(),
                Comment.builder()
                        .id(5)
                        .text("CommentText_5")
                        .book(expectedBooks.get(2))
                        .build(),
                Comment.builder()
                        .id(6)
                        .text("CommentText_6")
                        .book(expectedBooks.get(0))
                        .build());
    }

}
