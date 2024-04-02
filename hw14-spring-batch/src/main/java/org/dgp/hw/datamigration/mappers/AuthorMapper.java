package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.models.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    public AuthorMongo map(Author author) {
        return AuthorMongo.builder()
                .id(String.valueOf(author.getId()))
                .fullName(author.getFullName())
                .build();
    }
}
