package org.dgp.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceContextType;
import lombok.AllArgsConstructor;
import org.dgp.hw.models.Author;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private final EntityManager em;

    @Override
    public List<Author> findAll() {

        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }
}
