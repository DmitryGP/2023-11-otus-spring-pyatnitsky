package org.dgp.hw.dto;

import org.dgp.hw.models.Book;

public class BookDtoFactory {
    public static BookDto getBookDto(Book book) {
        var bookDto = new BookDto(book.getId(), book.getTitle(),
                new AuthorDto(book.getAuthor()), new GenreDto(book.getGenre()), null);

        var commentDtos = book.getComments().stream().map(c -> new CommentDto(c.getId(), c.getText(), bookDto)).toList();

        bookDto.setComments(commentDtos);

        return bookDto;
    }
}
