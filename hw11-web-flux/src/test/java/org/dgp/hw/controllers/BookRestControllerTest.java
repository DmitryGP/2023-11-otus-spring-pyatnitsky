package org.dgp.hw.controllers;

import org.dgp.hw.dto.BookCreateDto;
import org.dgp.hw.dto.BookDto;
import org.dgp.hw.dto.BookUpdateDto;
import org.dgp.hw.services.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BookRestController.class)
public class BookRestControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private WebTestClient client;

    @Captor
    private ArgumentCaptor<BookUpdateDto> bookUpdateDtoArgumentCaptor;

    @Captor
    private ArgumentCaptor<BookCreateDto> bookCreateDtoArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> bookIdArgumentCaptor;

    @Test
    void shouldReturnAllBooks() {

        var expectedBookCount = 2;

        var books = getBooks();
        when(bookService.findAll()).thenReturn(Flux.fromStream(books.stream()));

        var result = client.get()
                .uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                        .expectStatus().isOk()
                        .expectBodyList(BookDto.class);

        assertEquals(expectedBookCount, result.returnResult().getResponseBody().size());
    }

    @Test
    void shouldReturnRequestedBook() {

        var books = getBooks();
        when(bookService.findById(2)).thenReturn(
                Mono.just(
                        books.stream()
                                .filter(b -> b.getId() == 2)
                                .findFirst().get()));

        var result = client.get().uri("/api/v1/books/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class);

        var actualBook = result.returnResult().getResponseBody();

        assertEquals(2, actualBook.getId());
        assertEquals("Book2", actualBook.getTitle());
        assertEquals("Author2", actualBook.getAuthor());
        assertEquals("Genre2", actualBook.getGenre());

    }

    @Test
    void shouldReturnNotFoundStatusIfBookIsNotFound() {
        when(bookService.findById(5)).thenReturn(
                Mono.empty());

        client.get().uri("/api/v1/books/5")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldCreateBook() {
        var bookToCreate = new BookCreateDto("book", 1L, 1L);

        when(bookService.create(bookToCreate))
                .thenReturn(Mono.just(
                        new BookDto(1, "book", "author", "genre")));

        client.post().uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookToCreate)
                .exchange()
                .expectStatus().isOk();

        verify(bookService).create(bookCreateDtoArgumentCaptor.capture());

        var actualBook = bookCreateDtoArgumentCaptor.getValue();

        assertEquals("book", actualBook.getTitle());
        assertEquals(1, actualBook.getAuthorId());
        assertEquals(1, actualBook.getGenreId());
    }

    @Test
    void shouldUpdateBook() {
        var bookToUpdate = new BookUpdateDto(1L, "book");

        when(bookService.update(bookToUpdate))
                .thenReturn(Mono.just(
                        new BookDto(1, "book", "author", "genre")));

        client.put().uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(bookToUpdate)
                .exchange()
                .expectStatus().isOk();

        verify(bookService).update(bookUpdateDtoArgumentCaptor.capture());

        var actualBook = bookUpdateDtoArgumentCaptor.getValue();

        assertEquals(1, actualBook.getId());
        assertEquals("book", actualBook.getTitle());

    }

    @Test
    void shouldDeleteBook() {

        when(bookService.delete(1))
                .thenReturn(Mono.empty());

        client.delete().uri("/api/v1/books/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        verify(bookService).delete(bookIdArgumentCaptor.capture());

        var actualId = bookIdArgumentCaptor.getValue();

        assertEquals(1, actualId);
    }

    private static List<BookDto> getBooks() {
        return List.of(
                BookDto.builder()
                        .id(1)
                        .title("Book1")
                        .author("Author1")
                        .genre("Genre1")
                        .build(),
                BookDto.builder()
                        .id(2)
                        .title("Book2")
                        .author("Author2")
                        .genre("Genre2")
                        .build());
    }
}
