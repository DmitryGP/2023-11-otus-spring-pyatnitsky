package org.dgp.hw.services;

import org.dgp.hw.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
}
