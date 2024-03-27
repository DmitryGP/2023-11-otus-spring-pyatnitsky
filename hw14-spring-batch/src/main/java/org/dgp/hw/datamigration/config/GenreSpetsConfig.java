package org.dgp.hw.datamigration.config;

import jakarta.persistence.EntityManagerFactory;
import org.dgp.hw.datamigration.mappers.GenreMapper;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.datamigration.models.GenreTemp;
import org.dgp.hw.models.Genre;
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
    public MongoCursorItemReader<GenreTemp> tempGenreReader() {
        var reader = new MongoCursorItemReader<GenreTemp>();
        reader.setCollection("genreTemp");
        reader.setTargetType(GenreTemp.class);
        reader.setQuery(new Query());
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
}
