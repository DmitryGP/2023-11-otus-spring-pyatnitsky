package org.dgp.hw.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.mappers.GenreMapper;
import org.dgp.hw.repositories.GenreRepository;
import org.dgp.hw.utils.FallbackDataFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreMapper genreMapper;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "getGenresKey", fallbackMethod = "findAllFallback")
    public List<GenreDto> findAll() {
        return genreRepository.findAll().stream().map(genreMapper::toDto).toList();
    }

    public List<GenreDto> findAllFallback() {
        return List.of(FallbackDataFactory.createGenre());
    }
}
