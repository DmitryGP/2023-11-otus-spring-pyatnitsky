package org.dgp.hw.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "authors")
public class Author {

    @Id
    private final long id;

    private final String fullName;

    @PersistenceCreator
    public Author(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
