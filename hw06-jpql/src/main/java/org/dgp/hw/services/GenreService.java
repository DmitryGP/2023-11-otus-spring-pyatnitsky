package org.dgp.hw.services;

import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.models.Genre;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
