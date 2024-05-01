package org.dgp.hw.repositories;

import org.dgp.hw.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
