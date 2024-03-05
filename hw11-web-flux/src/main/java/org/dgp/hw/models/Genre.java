package org.dgp.hw.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(name = "genres")
public class Genre {

    @Id
    private final long id;

    private final String name;

    @PersistenceCreator
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
