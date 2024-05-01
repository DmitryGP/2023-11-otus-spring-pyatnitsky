package org.dgp.hw.repositories;

import org.dgp.hw.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "genre")
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findById(long id);

    @RestResource(path = "names", rel = "names")
    List<Genre> findByName(String name);
}
