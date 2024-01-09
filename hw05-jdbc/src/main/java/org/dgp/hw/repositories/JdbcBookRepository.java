package org.dgp.hw.repositories;

import lombok.AllArgsConstructor;
import org.dgp.hw.exceptions.EntityNotFoundException;
import org.dgp.hw.models.Author;
import org.dgp.hw.models.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.dgp.hw.models.Book;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Book> findById(long id) {

        Map<String, Object> params = Collections.singletonMap("id", id);

        var result = jdbcOperations.query("""
                                                    select books.id, books.title, books.author_id, books.genre_id, 
                                                    authors.full_name author_full_name, genres.name genre_name  
                                                    from books 
                                                    join authors on books.author_id = authors.id 
                                                    join genres on books.genre_id = genres.id 
                                                    where books.id = :id""",
                                            params, new BookRowMapper());

        if (CollectionUtils.isEmpty(result)) {
            return Optional.empty();
        }

        return Optional.of(result.getFirst());
    }

    @Override
    public List<Book> findAll() {
        var books = jdbcOperations.query("""
                                                    select books.id, books.title, books.author_id, books.genre_id, 
                                                    authors.full_name author_full_name, genres.name genre_name  
                                                    from books 
                                                    join authors on books.author_id = authors.id 
                                                    join genres on books.genre_id = genres.id""",
                                            new BookRowMapper());

        return books;
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        jdbcOperations.update("delete from books where id = :id", params);
    }

    private Book insert(Book book) {

        var params = new MapSqlParameterSource();

        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        var keyHolder = new GeneratedKeyHolder();

        jdbcOperations.update("""
                        insert into books (title, author_id, genre_id) 
                        values(:title, :author_id, :genre_id)""",
                        params, keyHolder,new String[]{"id"});

        book.setId(keyHolder.getKeyAs(Long.class));

        return book;
    }

    private Book update(Book book) {

        var params = Map.of("id", book.getId(),
                            "title", book.getTitle(),
                            "author_id", book.getAuthor().getId(),
                            "genre_id", book.getGenre().getId());

        var count = jdbcOperations.update("""
                                                update books 
                                                set title = :title,
                                                    author_id = :author_id,
                                                    genre_id = :genre_id
                                                where id = :id""", params);
        if (count == 0) {
            throw new EntityNotFoundException(String.format("Book with id = %s was not updated", book.getId()));
        }

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_full_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            long bookId = rs.getLong("id");
            String bookTitle = rs.getString("title");

            var author = new Author(authorId, authorName);
            var genre = new Genre(genreId, genreName);
            var book = new Book(bookId, bookTitle, author, genre);

            return book;
        }
    }
}
