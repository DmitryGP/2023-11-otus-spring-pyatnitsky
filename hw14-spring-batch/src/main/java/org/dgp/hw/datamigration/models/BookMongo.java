package org.dgp.hw.datamigration.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "book")
public class BookMongo {

    @Id
    private String id;

    private String title;

    private String authorId;

    private String genreId;
}
