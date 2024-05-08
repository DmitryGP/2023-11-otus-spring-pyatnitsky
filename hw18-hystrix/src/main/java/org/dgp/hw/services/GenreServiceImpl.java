package org.dgp.hw.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.mappers.GenreMapper;
import org.dgp.hw.repositories.GenreRepository;
import org.dgp.hw.utils.FallbackDataFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    @CircuitBreaker(name = "getDataCircuitBreaker", fallbackMethod = "findAllFallback")
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).toList();
    }

    public List<GenreDto> findAllFallback(Exception exc) {
        return List.of(FallbackDataFactory.createGenre());
    }
}
