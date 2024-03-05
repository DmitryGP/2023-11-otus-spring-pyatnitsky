package org.dgp.hw.repositories;

import io.r2dbc.spi.Readable;
import lombok.AllArgsConstructor;
import org.dgp.hw.dto.BookDto;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@AllArgsConstructor
public class BookRepositoryCustom {

    private static final String SQL_ALL = """
            select b.id as id, b.title as title, a.full_name as author, g.name as genre
            from books b
                inner join authors a
                    on b.author_id = a.id
                inner join genres g
                    on b.genre_id = g.id
            """;

    private final R2dbcEntityTemplate template;

    public Flux<BookDto> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(SQL_ALL)
                        .execute())
                        .flatMap(result -> result.map(this::map)));
    }

    private BookDto map(Readable record) {
        return new BookDto(record.get("id", Long.class),
                record.get("title", String.class),
                record.get("author", String.class),
                record.get("genre", String.class));
    }
}
