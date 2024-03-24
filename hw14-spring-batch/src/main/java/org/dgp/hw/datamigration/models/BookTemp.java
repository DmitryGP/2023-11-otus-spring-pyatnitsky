package org.dgp.hw.datamigration.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "bookTemp")
public class BookTemp {

    @Id
    private String Id;

    private String mongoId;

    private String title;

    private long authorId;

    private long genreId;


}
