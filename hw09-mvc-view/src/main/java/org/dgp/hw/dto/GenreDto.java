package org.dgp.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dgp.hw.models.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenreDto {

    private long id;

    private String name;

    public GenreDto(Genre genre) {
        this(genre.getId(), genre.getName());
    }
}
