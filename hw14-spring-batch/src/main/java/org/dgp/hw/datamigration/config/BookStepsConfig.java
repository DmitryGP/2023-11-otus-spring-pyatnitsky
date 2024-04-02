package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.BookMapper;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.models.Book;
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
public class BookStepsConfig {
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
    public JpaCursorItemReader<Book> bookReader() {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookMongo> mongoBookProcessor(BookMapper mapper) {
        return mapper::map;
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

    @Bean
    public Step mongoBookTransformStep(JpaCursorItemReader<Book> bookReader,
                                       ItemProcessor<Book, BookMongo> mongoBookProcessor,
                                       MongoItemWriter<BookMongo> bookMongoWriter) {
        return new StepBuilder("mongoTransformBook", jobRepository)
                .<Book, BookMongo>chunk(3, platformTransactionManager)
                .reader(bookReader)
                .processor(mongoBookProcessor)
                .writer(bookMongoWriter)
                .build();
    }
}
