package org.dgp.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Comment;
import org.dgp.hw.models.Genre;

@ChangeLog(order = "001")
public class InitMongoDbDataChangeLog {

    @ChangeSet(order = "000", id = "dropDb", author = "pyatnitskiyDmitry", runAlways = true)
    public void dropDb(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initData", author = "pyatnitskiyDmitry", runAlways = true)
    public void initData(MongockTemplate template) {
        var author01 = template.save(Author.builder().fullName("Author_1").build());
        var author02 = template.save(Author.builder().fullName("Author_2").build());
        var author03 = template.save(Author.builder().fullName("Author_3").build());
        var genre01 = template.save(Genre.builder().name("Genre_1").build());
        var genre02 = template.save(Genre.builder().name("Genre_2").build());
        var genre03 = template.save(Genre.builder().name("Genre_3").build());
        var book01 = template.save(Book.builder().title("Book_1").genre(genre01).author(author01).build());
        var book02 = template.save(Book.builder().title("Book_2").genre(genre02).author(author02).build());
        var book03 = template.save(Book.builder().title("Book_3").genre(genre03).author(author03).build());
        template.save(Comment.builder().text("Text_1").book(book01).build());
        template.save(Comment.builder().text("Text_2").book(book02).build());
        template.save(Comment.builder().text("Text_3").book(book03).build());
        template.save(Comment.builder().text("Text_4").book(book01).build());
        template.save(Comment.builder().text("Text_5").book(book03).build());
    }
}
