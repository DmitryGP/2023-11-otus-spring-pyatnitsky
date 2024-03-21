package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(value = "book-graph")
    @PostFilter("hasPermission(filterObject, 'READ')")
    @NotNull
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book-graph")
    @NotNull
    Optional<Book> findById(Long id);

    @Override
    @NotNull
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    Book save(@Param("book") Book book);


}
