package org.dgp.hw.repositories;

import org.dgp.hw.dto.BookDto;
import org.dgp.hw.models.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends R2dbcRepository<Book, Long> {

    @Query("""
    select b.id id, b.title title, a.fullName author, g.name genre 
    from Book b
        inner join Author a
                    on b.authorId = a.id
        inner join Genre g
                    on b.genreId = g.id 
    """)
    Flux<BookDto> getBooks();
}
