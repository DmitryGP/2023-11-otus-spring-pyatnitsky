package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(attributePaths = {"author", "genre"})
    List<Book> findAll();
}
