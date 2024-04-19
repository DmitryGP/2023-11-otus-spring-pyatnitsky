package org.dgp.hw.changelogs;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.dgp.hw.models.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;


@RequiredArgsConstructor
public class TestDataInitializer {

    public void dropDb(MongoTemplate template) {
        template.dropCollection(Comment.class);
        template.dropCollection(Book.class);
        template.dropCollection(Author.class);
        template.dropCollection(Genre.class);
    }

    public void initData(MongoTemplate template) {
        var author01 = template.save(Author.builder().id("1").fullName("Author_1").build());
        var author02 = template.save(Author.builder().id("2").fullName("Author_2").build());
        var author03 = template.save(Author.builder().id("3").fullName("Author_3").build());
        var genre01 = template.save(Genre.builder().id("1").name("Genre_1").build());
        var genre02 = template.save(Genre.builder().id("2").name("Genre_2").build());
        var genre03 = template.save(Genre.builder().id("3").name("Genre_3").build());
        var book01 = template.save(Book.builder().id("1").title("Book_1").genre(genre01).author(author01).build());
        var book02 = template.save(Book.builder().id("2").title("Book_2").genre(genre02).author(author02).build());
        var book03 = template.save(Book.builder().id("3").title("Book_3").genre(genre03).author(author03).build());
        template.save(Comment.builder().id("1").text("Text_1").book(book01).build());
        template.save(Comment.builder().id("2").text("Text_2").book(book02).build());
        template.save(Comment.builder().id("3").text("Text_3").book(book03).build());
        template.save(Comment.builder().id("4").text("Text_4").book(book01).build());
        template.save(Comment.builder().id("5").text("Text_5").book(book03).build());
    }
}
