package org.dgp.hw.datamigration.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@Import(value = {AuthorStepsConfig.class,
        GenreSpetsConfig.class,
        BookStepsConfig.class,
        CommentStepsConfig.class})
public class JobConfig {

    public static final String IMPORT_BOOKS_JOB_NAME = "ImportBooks";

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private Step mongoAuthorTransformStep;

    @Autowired
    private Step mongoGenreTransformStep;

    @Autowired
    private Step mongoBookTransformStep;

    @Autowired
    private Step mongoCommentTransformStep;


    @Bean
    public Job importBooksJob() {

        return new JobBuilder(IMPORT_BOOKS_JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(mongoAuthorTransformStep)
                .next(mongoGenreTransformStep)
                .next(mongoBookTransformStep)
                .next(mongoCommentTransformStep)
                .end()
                .listener(new ImportBooksJobListener())
                .build();
    }
}
