package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.datamigration.mappers.AuthorMapper;
import org.dgp.hw.datamigration.mappers.BookMapper;
import org.dgp.hw.datamigration.mappers.CommentMapper;
import org.dgp.hw.datamigration.mappers.GenreMapper;
import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.datamigration.models.AuthorTemp;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.datamigration.models.BookTemp;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.CommentTemp;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.datamigration.models.GenreTemp;
import org.dgp.hw.datamigration.service.BookMongoItemProcessor;
import org.dgp.hw.datamigration.service.CleanUpService;
import org.dgp.hw.datamigration.service.CommentMongoItemProcessor;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.dgp.hw.models.Genre;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class JobConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CleanUpService cleanUpService;

    @StepScope
    @Bean
    public JpaCursorItemReader<Author> authorReader() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from Author a")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorTemp> tempAuthorProcessor(AuthorMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<AuthorTemp> authorTempWriter() {
        var writer = new MongoItemWriter<AuthorTemp>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("authorTemp");

        return writer;
    }

    @StepScope
    @Bean
    public Step tempAuthorTransformStep(JpaCursorItemReader<Author> authorReader,
                                        ItemProcessor<Author, AuthorTemp> tempAuthorProcessor,
                                        MongoItemWriter<AuthorTemp> authorTempWriter) {
        return new StepBuilder("tempTransformAuthor", jobRepository)
                .<Author, AuthorTemp>chunk(3, platformTransactionManager)
                .reader(authorReader)
                .processor(tempAuthorProcessor)
                .writer(authorTempWriter)
                .build();
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<Author> genreReader() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select g from Genre g")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreTemp> tempGenreProcessor(GenreMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<GenreTemp> genreTempWriter() {
        var writer = new MongoItemWriter<GenreTemp>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("genreTemp");

        return writer;
    }

    @StepScope
    @Bean
    public Step tempGenreTransformStep(JpaCursorItemReader<Genre> genreReader,
                                        ItemProcessor<Genre, GenreTemp> tempGenreProcessor,
                                        MongoItemWriter<GenreTemp> genreTempWriter) {
        return new StepBuilder("tempTransformGenre", jobRepository)
                .<Genre, GenreTemp>chunk(3, platformTransactionManager)
                .reader(genreReader)
                .processor(tempGenreProcessor)
                .writer(genreTempWriter)
                .build();
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<Comment> commentReader() {
        return new JpaCursorItemReaderBuilder<Comment>()
                .name("commentReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Comment c")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Comment, CommentTemp> tempCommentProcessor(CommentMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<CommentTemp> commentTempWriter() {
        var writer = new MongoItemWriter<CommentTemp>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("commentTemp");

        return writer;
    }

    @StepScope
    @Bean
    public Step tempCommentTransformStep(JpaCursorItemReader<Comment> commentReader,
                                       ItemProcessor<Comment, CommentTemp> tempCommentProcessor,
                                       MongoItemWriter<CommentTemp> commentTempWriter) {
        return new StepBuilder("tempTransformComment", jobRepository)
                .<Comment, CommentTemp>chunk(3, platformTransactionManager)
                .reader(commentReader)
                .processor(tempCommentProcessor)
                .writer(commentTempWriter)
                .build();
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<Book> bookReader() {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookTemp> tempBookProcessor(BookMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<BookTemp> bookTempWriter() {
        var writer = new MongoItemWriter<BookTemp>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("bookTemp");

        return writer;
    }

    @StepScope
    @Bean
    public Step tempBookTransformStep(JpaCursorItemReader<Book> bookReader,
                                         ItemProcessor<Book, BookTemp> tempBookProcessor,
                                         MongoItemWriter<BookTemp> bookTempWriter) {
        return new StepBuilder("tempTransformBook", jobRepository)
                .<Book, BookTemp>chunk(3, platformTransactionManager)
                .reader(bookReader)
                .processor(tempBookProcessor)
                .writer(bookTempWriter)
                .build();
    }

    @StepScope
    @Bean
    public MongoCursorItemReader<AuthorTemp> tempAuthorReader() {
        var reader = new MongoCursorItemReader<AuthorTemp>();
        reader.setCollection("authorTemp");
        reader.setTemplate(mongoOperations);

        return reader;
    }

    @StepScope
    @Bean
    public ItemProcessor<AuthorTemp, AuthorMongo> mongoAuthorProcessor(AuthorMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<AuthorMongo> authorMongoWriter() {
        var writer = new MongoItemWriter<AuthorMongo>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("author");

        return writer;
    }

    @StepScope
    @Bean
    public Step mongoAuthorTransformStep(MongoCursorItemReader<AuthorTemp> tempAuthorReader,
                                      ItemProcessor<AuthorTemp, AuthorMongo> mongoAuthorProcessor,
                                      MongoItemWriter<AuthorMongo> authorMongoWriter) {
        return new StepBuilder("mongoTransformAuthor", jobRepository)
                .<AuthorTemp, AuthorMongo>chunk(3, platformTransactionManager)
                .reader(tempAuthorReader)
                .processor(mongoAuthorProcessor)
                .writer(authorMongoWriter)
                .build();
    }

    @StepScope
    @Bean
    public MongoCursorItemReader<GenreTemp> tempGenreReader() {
        var reader = new MongoCursorItemReader<GenreTemp>();
        reader.setCollection("genreTemp");
        reader.setTemplate(mongoOperations);

        return reader;
    }

    @StepScope
    @Bean
    public ItemProcessor<GenreTemp, GenreMongo> mongoGenreProcessor(GenreMapper mapper) {
        return mapper::map;
    }

    @StepScope
    @Bean
    public MongoItemWriter<GenreMongo> genreMongoWriter() {
        var writer = new MongoItemWriter<GenreMongo>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("genre");

        return writer;
    }

    @StepScope
    @Bean
    public Step mongoGenreTransformStep(MongoCursorItemReader<GenreTemp> tempGenreReader,
                                         ItemProcessor<GenreTemp, GenreMongo> mongoGenreProcessor,
                                         MongoItemWriter<GenreMongo> genreMongoWriter) {
        return new StepBuilder("mongoTransformGenre", jobRepository)
                .<GenreTemp, GenreMongo>chunk(3, platformTransactionManager)
                .reader(tempGenreReader)
                .processor(mongoGenreProcessor)
                .writer(genreMongoWriter)
                .build();
    }

    @StepScope
    @Bean
    public MongoCursorItemReader<BookTemp> tempBookReader() {
        var reader = new MongoCursorItemReader<BookTemp>();
        reader.setCollection("bookTemp");
        reader.setTemplate(mongoOperations);

        return reader;
    }

    @StepScope
    @Bean
    public ItemProcessor<BookTemp, BookMongo> mongoBookProcessor(BookMapper bookMapper) {
        return new BookMongoItemProcessor(mongoOperations, bookMapper);
    }

    @StepScope
    @Bean
    public MongoItemWriter<BookMongo> bookMongoWriter() {
        var writer = new MongoItemWriter<BookMongo>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("book");

        return writer;
    }

    @StepScope
    @Bean
    public Step mongoBookTransformStep(MongoCursorItemReader<BookTemp> tempBookReader,
                                        ItemProcessor<BookTemp, BookMongo> mongoBookProcessor,
                                        MongoItemWriter<BookMongo> bookMongoWriter) {
        return new StepBuilder("mongoTransformGenre", jobRepository)
                .<BookTemp, BookMongo>chunk(3, platformTransactionManager)
                .reader(tempBookReader)
                .processor(mongoBookProcessor)
                .writer(bookMongoWriter)
                .build();
    }

    @StepScope
    @Bean
    public MongoCursorItemReader<CommentTemp> tempCommentReader() {
        var reader = new MongoCursorItemReader<CommentTemp>();
        reader.setCollection("commentTemp");
        reader.setTemplate(mongoOperations);

        return reader;
    }

    @StepScope
    @Bean
    public ItemProcessor<CommentTemp, CommentMongo> mongoCommentProcessor(CommentMapper commentMapper) {
        return new CommentMongoItemProcessor(mongoOperations, commentMapper);
    }

    @StepScope
    @Bean
    public MongoItemWriter<CommentMongo> commentMongoWriter() {
        var writer = new MongoItemWriter<CommentMongo>();

        writer.setMode(MongoItemWriter.Mode.INSERT);
        writer.setTemplate(mongoOperations);
        writer.setCollection("comment");

        return writer;
    }

    @StepScope
    @Bean
    public Step mongoCommentTransformStep(MongoCursorItemReader<CommentTemp> tempCommentReader,
                                       ItemProcessor<CommentTemp, CommentMongo> mongoCommentProcessor,
                                       MongoItemWriter<CommentMongo> commentMongoWriter) {
        return new StepBuilder("mongoTransformGenre", jobRepository)
                .<CommentTemp, CommentMongo>chunk(3, platformTransactionManager)
                .reader(tempCommentReader)
                .processor(mongoCommentProcessor)
                .writer(commentMongoWriter)
                .build();
    }

    @Bean
    public Step cleanUpStep() {
        return new StepBuilder("cleanUpStep", jobRepository)
                .tasklet(cleanUpTasklet(), platformTransactionManager)
                .build();

    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Job importBooksJob(Step tempAuthorTransformStep,
                                Step tempGenreTransformStep,
                              Step tempBookTransformStep,
                              Step tempCommentTransformStep,
                              Step mongoAuthorTransformStep,
                              Step mongoGenreTransformStep,
                              Step mongoBookTransformStep,
                              Step mongoCommentTransformStep,
                              Step cleanUpStep) {

        return new JobBuilder("ImportBooks", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(tempAuthorTransformStep)
                .next(tempGenreTransformStep)
                .next(tempBookTransformStep)
                .next(tempCommentTransformStep)
                .next(mongoAuthorTransformStep)
                .next(mongoGenreTransformStep)
                .next(mongoBookTransformStep)
                .next(mongoCommentTransformStep)
                .next(cleanUpStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.atInfo()
                                .setMessage("Job {} started.")
                                .addArgument(jobExecution.getJobInstance().getJobName())
                                .log();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.atInfo()
                                .setMessage("Job {} finished.")
                                .addArgument(jobExecution.getJobInstance().getJobName())
                                .log();
                    }
                })
                .build();
    }
}
