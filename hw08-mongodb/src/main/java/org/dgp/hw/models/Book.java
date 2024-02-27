package org.dgp.hw.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor()
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "books")
public class Book {

    @Id
    private long id;

    private String title;

    private Author author;

    private Genre genre;

}
