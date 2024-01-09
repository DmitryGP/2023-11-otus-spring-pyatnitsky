package org.dgp.hw.converters;

import org.springframework.stereotype.Component;
import org.dgp.hw.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
