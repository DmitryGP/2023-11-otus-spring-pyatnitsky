package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "author")
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findById(long id);

    @RestResource(path = "fullNames", rel = "fullNames")
    List<Author> findByFullName(String fullName);
}
