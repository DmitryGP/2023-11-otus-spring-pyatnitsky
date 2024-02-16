package org.dgp.hw.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreateDto {

    private Long id;

    @NotBlank
    private String title;

    private AuthorDto author;

    private GenreDto genre;
}
