package org.dgp.hw.datamigration.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "comment")
public class CommentMongo {
    @Id
    private String id;

    private String text;

    private String bookId;
}
