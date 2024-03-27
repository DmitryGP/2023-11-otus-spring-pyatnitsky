package org.dgp.hw.services;

import org.dgp.hw.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findAll();
}
