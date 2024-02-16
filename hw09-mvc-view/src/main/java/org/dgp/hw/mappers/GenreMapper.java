package org.dgp.hw.mappers;

import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.models.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public Genre toModel(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }
}
