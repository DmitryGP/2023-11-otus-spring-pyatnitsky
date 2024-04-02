package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.AuthorMapper;
import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.models.Author;
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
public class AuthorStepsConfig {
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
    public JpaCursorItemReader<Author> authorReader() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from Author a")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Author, AuthorMongo> mongoAuthorProcessor(AuthorMapper mapper) {
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

    @Bean
    public Step mongoAuthorTransformStep(JpaCursorItemReader<Author> authorReader,
                                         ItemProcessor<Author, AuthorMongo> mongoAuthorProcessor,
                                         MongoItemWriter<AuthorMongo> authorMongoWriter) {
        return new StepBuilder("mongoTransformAuthor", jobRepository)
                .<Author, AuthorMongo>chunk(3, platformTransactionManager)
                .reader(authorReader)
                .processor(mongoAuthorProcessor)
                .writer(authorMongoWriter)
                .build();
    }
}
