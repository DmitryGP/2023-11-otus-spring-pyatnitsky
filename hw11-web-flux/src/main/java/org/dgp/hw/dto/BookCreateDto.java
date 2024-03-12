package org.dgp.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookCreateDto {

    private String title;

    private Long authorId;

    private Long genreId;
}
