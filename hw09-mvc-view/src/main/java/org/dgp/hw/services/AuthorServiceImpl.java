package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.mappers.AuthorMapper;
import org.dgp.hw.repositories.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream().map(authorMapper::toDto).toList();
    }
}
