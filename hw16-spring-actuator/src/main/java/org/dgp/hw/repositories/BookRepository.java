package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.dgp.hw.models.CustomBook;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = CustomBook.class)
public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "book-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book-graph")
    Optional<Book> findById(Long id);

    @RestResource(path = "titles", rel = "titles")
    List<Book> findByTitle(String title);
}
