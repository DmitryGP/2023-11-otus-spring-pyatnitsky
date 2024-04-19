/*package org.dgp.hw.repositories;

import lombok.AllArgsConstructor;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Book;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final MongoTemplate mongoTemplate;
    @Override
    public Optional<Author> findById(String id) {
        var query  = Query.query(Criteria.where("author.id").is(id));

        var book = mongoTemplate.find(query, Book.class)
                .stream().findFirst();

        return book.map(Book::getAuthor);
    }

    @Override
    public List<Author> findAll() {
        var books = mongoTemplate.findAll(Book.class);

        return books.stream().map(Book::getAuthor).distinct().toList();
    }
}*/
