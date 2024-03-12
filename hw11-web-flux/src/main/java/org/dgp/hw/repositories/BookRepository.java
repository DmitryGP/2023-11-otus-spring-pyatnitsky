package org.dgp.hw.repositories;

import org.dgp.hw.dto.BookDto;
import org.dgp.hw.models.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends ReactiveCrudRepository<Book, Long> {

    @Query("""
    select b.id id, b.title title, a.full_name author, g.name genre 
    from books b
        inner join authors a
                    on b.author_id = a.id
        inner join genres g
                    on b.genre_id = g.id 
    """)
    Flux<BookDto> findAllBooks();
}
