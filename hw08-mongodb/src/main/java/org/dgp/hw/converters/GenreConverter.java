package org.dgp.hw.converters;

import org.dgp.hw.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter {
    public String genreToString(GenreDto genre) {
        return "Id: %s, Name: %s".formatted(genre.getId(), genre.getName());
    }
}
