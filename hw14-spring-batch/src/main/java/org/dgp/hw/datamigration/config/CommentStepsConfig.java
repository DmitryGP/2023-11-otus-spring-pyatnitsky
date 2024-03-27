package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.CommentMapper;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.CommentTemp;
import org.dgp.hw.datamigration.service.CommentMongoItemProcessor;
import org.dgp.hw.models.Comment;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoCursorItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
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
    public MongoCursorItemReader<CommentTemp> tempCommentReader() {
        var reader = new MongoCursorItemReader<CommentTemp>();
        reader.setCollection("commentTemp");
        reader.setTargetType(CommentTemp.class);
        reader.setQuery(new Query());
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

    @Bean
    public Step mongoCommentTransformStep(MongoCursorItemReader<CommentTemp> tempCommentReader,
                                          ItemProcessor<CommentTemp, CommentMongo> mongoCommentProcessor,
                                          MongoItemWriter<CommentMongo> commentMongoWriter) {
        return new StepBuilder("mongoTransformComment", jobRepository)
                .<CommentTemp, CommentMongo>chunk(3, platformTransactionManager)
                .reader(tempCommentReader)
                .processor(mongoCommentProcessor)
                .writer(commentMongoWriter)
                .build();
    }
}
