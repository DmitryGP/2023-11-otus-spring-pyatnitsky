/*package org.dgp.hw.repositories;

import lombok.AllArgsConstructor;
import org.dgp.hw.models.Book;
import org.dgp.hw.models.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class GenreRepositoryImpl implements GenreRepository{

    private final MongoTemplate mongoTemplate;
    @Override
    public Optional<Genre> findById(String id) {
        var query  = Query.query(Criteria.where("genre.id").is(id));

        var book = mongoTemplate.find(query, Book.class)
                .stream().findFirst();

        return book.map(Book::getGenre);
    }

    @Override
    public List<Genre> findAll() {
        var books = mongoTemplate.findAll(Book.class);

        return books.stream().map(Book::getGenre).distinct().toList();
    }
}*/
