package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.GenreMapper;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.models.Genre;
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
public class GenreSpetsConfig {
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
    public JpaCursorItemReader<Genre> genreReader() {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("genreReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select g from Genre g")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreMongo> mongoGenreProcessor(GenreMapper mapper) {
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

    @Bean
    public Step mongoGenreTransformStep(JpaCursorItemReader<Genre> genreReader,
                                        ItemProcessor<Genre, GenreMongo> mongoGenreProcessor,
                                        MongoItemWriter<GenreMongo> genreMongoWriter) {
        return new StepBuilder("mongoTransformGenre", jobRepository)
                .<Genre, GenreMongo>chunk(3, platformTransactionManager)
                .reader(genreReader)
                .processor(mongoGenreProcessor)
                .writer(genreMongoWriter)
                .build();
    }
}
