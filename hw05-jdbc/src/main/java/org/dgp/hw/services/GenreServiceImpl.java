package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dgp.hw.models.Genre;
import org.dgp.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
