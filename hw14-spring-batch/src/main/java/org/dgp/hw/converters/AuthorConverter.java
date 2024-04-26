package org.dgp.hw.converters;

import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.dto.AuthorDto;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }

    public String mongoAuthorToString(AuthorMongo author) {
        return "Id: %s, Full Name: %s".formatted(
                author.getId(),
                author.getFullName());
    }
}
