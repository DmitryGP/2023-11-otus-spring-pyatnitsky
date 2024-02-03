package org.dgp.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.dgp.hw.models.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
@AllArgsConstructor
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Book> findById(long id) {

        var graph = em.getEntityGraph("book-graph");

        var query = em.createQuery("select b from Book b where b.Id = :id", Book.class);

        query.setParameter("id", id);

        query.setHint(FETCH.getKey(), graph);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {

        var graph = em.getEntityGraph("book-graph");

        var query = em.createQuery("select b from Book b", Book.class);

        query.setHint(FETCH.getKey(), graph);

        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        }

        var savedBook = em.merge(book);

        return savedBook;
    }

    @Override
    public void deleteById(long id) {
        var book = em.find(Book.class, id);

        if (book != null) {
            em.remove(book);
        }
    }
}