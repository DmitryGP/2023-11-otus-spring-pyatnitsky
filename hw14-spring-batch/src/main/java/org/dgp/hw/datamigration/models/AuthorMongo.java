package org.dgp.hw.datamigration.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "author")
public class AuthorMongo {

    @Id
    private String id;

    private String fullName;
}
