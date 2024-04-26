package org.dgp.hw.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dgp.hw.converters.AuthorConverter;
import org.dgp.hw.converters.BookConverter;
import org.dgp.hw.converters.CommentConverter;
import org.dgp.hw.converters.GenreConverter;
import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.datamigration.models.BookMongo;
import org.dgp.hw.datamigration.models.CommentMongo;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
@Slf4j
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job importBooksJob;

    private final MongoOperations mongoOperations;

    private final BookConverter bookConverter;

    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "startMigrationJobWithJobLauncher", key = "sm-jl")
    public void startMigrationJobWithJobLauncher() throws Exception {
        JobExecution execution = jobLauncher.run(importBooksJob, new JobParametersBuilder().toJobParameters());

        log.atInfo()
                .setMessage(execution.toString())
                .log();
    }

    @ShellMethod(value = "getMigratedBooks", key = "get-mb")
    public String getMigratedBooks() {
        var books = mongoOperations.find(new Query(), BookMongo.class);

        return books.stream().map(bookConverter::mongoBookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "getMigratedAuthors", key = "get-ma")
    public String getMigratedAuthors() {
        var authors = mongoOperations.find(new Query(), AuthorMongo.class);

        return authors.stream().map(authorConverter::mongoAuthorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "getMigratedGenres", key = "get-mg")
    public String getMigratedGenres() {
        var genres = mongoOperations.find(new Query(), GenreMongo.class);

        return genres.stream().map(genreConverter::mongoGenreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "getMigratedComments", key = "get-mc")
    public String getMigratedComments() {
        var comments = mongoOperations.find(new Query(), CommentMongo.class);

        return comments.stream().map(commentConverter::mongoCommentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
