package org.dgp.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dgp.hw.models.Author;
import org.dgp.hw.repositories.AuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
