package org.dgp.hw.repositories;

import io.r2dbc.spi.Row;
import org.dgp.hw.dto.BookDto;
import org.springframework.core.convert.converter.Converter;

public class BookToBookDtoConverter implements Converter<Row, BookDto> {
    @Override
    public BookDto convert(Row source) {
        return new BookDto(source.get("id", Long.class),
                source.get("title", String.class),
                source.get("author", String.class),
                source.get("genre", String.class));
    }
}
