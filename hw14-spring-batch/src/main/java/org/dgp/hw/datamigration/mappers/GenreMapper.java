package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.datamigration.models.GenreTemp;
import org.dgp.hw.datamigration.service.MongoIdGenerator;
import org.dgp.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    private final MongoIdGenerator mongoIdGenerator;

    public GenreTemp map(Genre genre) {
        return GenreTemp.builder()
                .id(String.valueOf(genre.getId()))
                .name(genre.getName())
                .mongoId(mongoIdGenerator.generateId())
                .build();
    }

    public GenreMongo map(GenreTemp genre) {
        return GenreMongo.builder()
                .id(genre.getMongoId())
                .name(genre.getName())
                .build();
    }
}
