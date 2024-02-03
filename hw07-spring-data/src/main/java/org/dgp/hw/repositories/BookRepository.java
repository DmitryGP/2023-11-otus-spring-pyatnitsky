package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "book-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book-graph")
    Optional<Book> findById(Long id);


}
