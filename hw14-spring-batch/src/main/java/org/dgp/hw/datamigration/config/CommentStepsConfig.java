package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.CommentMapper;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.models.Comment;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CommentStepsConfig {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private MongoOperations mongoOperations;

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
    public ItemProcessor<Comment, CommentMongo> mongoCommentProcessor(CommentMapper commentMapper) {
        return commentMapper::map;
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

    @Bean
    public Step mongoCommentTransformStep(JpaCursorItemReader<Comment> commentReader,
                                          ItemProcessor<Comment, CommentMongo> mongoCommentProcessor,
                                          MongoItemWriter<CommentMongo> commentMongoWriter) {
        return new StepBuilder("mongoTransformComment", jobRepository)
                .<Comment, CommentMongo>chunk(3, platformTransactionManager)
                .reader(commentReader)
                .processor(mongoCommentProcessor)
                .writer(commentMongoWriter)
                .build();
    }
}
