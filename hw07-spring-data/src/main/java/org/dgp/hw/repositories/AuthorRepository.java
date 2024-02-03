package org.dgp.hw.repositories;

import org.dgp.hw.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findById(long id);
}
