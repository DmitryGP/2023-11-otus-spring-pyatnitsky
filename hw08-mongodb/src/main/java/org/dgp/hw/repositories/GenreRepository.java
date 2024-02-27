package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> findById(long id);

    List<Genre> findAll();
}
