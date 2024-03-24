package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.AuthorMongo;
import org.dgp.hw.datamigration.models.AuthorTemp;
import org.dgp.hw.datamigration.service.MongoIdGenerator;
import org.dgp.hw.models.Author;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMapper {

    private final MongoIdGenerator mongoIdGenerator;

    public AuthorTemp map(Author author){
        return AuthorTemp.builder()
                .mongoId(mongoIdGenerator.generateId())
                .id(String.valueOf(author.getId()))
                .fullName(author.getFullName())
                .build();
    }


    public AuthorMongo map(AuthorTemp author) {
        return AuthorMongo.builder()
                .id(author.getMongoId())
                .fullName(author.getFullName())
                .build();
    }
}
