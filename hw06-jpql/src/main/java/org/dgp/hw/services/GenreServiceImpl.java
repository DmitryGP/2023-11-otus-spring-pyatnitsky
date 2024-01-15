package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.models.Genre;
import org.dgp.hw.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
}
