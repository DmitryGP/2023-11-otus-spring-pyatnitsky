package org.dgp.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreateDto {


    @NotBlank
    private String title;

    @NotNull
    private AuthorDto author;

    @NotNull
    private GenreDto genre;
}
