package org.dgp.hw.models;


import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Table(name = "books")
public class Book {

    @Id
    private final long id;

    private final String title;

    private final Long authorId;

    private final Long genreId;

    @PersistenceCreator
    public Book(Long id, String title, Long authorId, Long genreId) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
    }


}
