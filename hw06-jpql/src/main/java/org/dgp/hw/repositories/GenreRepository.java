package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findAll();

    Optional<Genre> findById(long id);
}
