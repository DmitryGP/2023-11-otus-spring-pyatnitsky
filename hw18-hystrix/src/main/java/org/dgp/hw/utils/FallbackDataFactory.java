package org.dgp.hw.utils;

import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.CommentDto;
import org.dgp.hw.dto.GenreDto;

public class FallbackDataFactory {

    private static String NA = "N/A";

    public static CommentDto createComment() {
        var book = createBook();

        return new CommentDto(-1, NA,
                book);
    }

    public static BookDto createBook() {
        var author = createAuthor();
        var genre = createGenre();

        return new BookDto(-1, NA,
                author,
                genre);
    }

    public static AuthorDto createAuthor() {
        return new AuthorDto(-1, NA);
    }

    public static GenreDto createGenre() {
        return new GenreDto(-1, NA);
    }
}
