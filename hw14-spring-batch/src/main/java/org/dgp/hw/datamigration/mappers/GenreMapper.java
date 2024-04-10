package org.dgp.hw.datamigration.mappers;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenreMapper {

    public GenreMongo map(Genre genre) {
        return GenreMongo.builder()
                .id(String.valueOf(genre.getId()))
                .name(genre.getName())
                .build();
    }
}
