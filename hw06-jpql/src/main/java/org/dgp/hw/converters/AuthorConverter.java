package org.dgp.hw.converters;

import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.models.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "Id: %d, FullName: %s".formatted(author.getId(), author.getFullName());
    }
}
