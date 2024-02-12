package org.dgp.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dgp.hw.models.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

    private long id;

    private String fullName;

    public AuthorDto(Author author) {
        this(author.getId(), author.getFullName());
    }

}
