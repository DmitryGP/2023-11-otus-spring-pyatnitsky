package org.dgp.hw.controllers;

import org.dgp.hw.dto.AuthorDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.GenreDto;
import org.dgp.hw.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllBooks() throws Exception {
        var books = getBooks();
        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnRequestedBook() throws Exception {

        var books = getBooks();
        when(bookService.findById(2)).thenReturn(books.stream().filter(b -> b.getId() == 2).findFirst());

        mockMvc.perform(get("/api/v1/books/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.title").value("Book2"))
                .andExpect(jsonPath("$.author.id").value(2))
                .andExpect(jsonPath("$.author.fullName").value("Author2"))
                .andExpect(jsonPath("$.genre.id").value(2))
                .andExpect(jsonPath("$.genre.name").value("Genre2"));

    }

    @Test
    void shouldFailWhenRequestWrongBookId() throws Exception {
        mockMvc.perform(get("/api/v1/books/4"))
                .andExpect(status().is4xxClientError());
    }

    private static List<BookDto> getBooks() {
        return List.of(
                BookDto.builder()
                        .id(1)
                        .title("Book1")
                        .author(AuthorDto.builder()
                                .id(1)
                                .fullName("Author1").build())
                        .genre(GenreDto.builder()
                                .id(1)
                                .name("Genre1").build())
                        .build(),
                BookDto.builder()
                        .id(2)
                        .title("Book2")
                        .author(AuthorDto.builder()
                                .id(2)
                                .fullName("Author2").build())
                        .genre(GenreDto.builder()
                                .id(2)
                                .name("Genre2").build())
                        .build());
    }
}
