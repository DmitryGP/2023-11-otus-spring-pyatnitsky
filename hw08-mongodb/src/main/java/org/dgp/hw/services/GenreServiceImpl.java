package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() {

        return genreRepository.findAll()
                .stream()
                .map(GenreDto::new).toList();
    }
}
