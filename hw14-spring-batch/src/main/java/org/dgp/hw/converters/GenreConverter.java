package org.dgp.hw.converters;

import org.dgp.hw.datamigration.models.GenreMongo;
import org.dgp.hw.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %d, Name: %s".formatted(
                genre.getId(),
                genre.getName());
    }

    public String mongoGenreToString(GenreMongo genre) {
        return "Id: %s, Name: %s".formatted(
                genre.getId(),
                genre.getName());
    }
}
