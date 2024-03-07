package org.dgp.hw.controllers;

import org.dgp.hw.dto.BookDto;
import org.dgp.hw.repositories.BookRepositoryCustom;
import org.dgp.hw.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(
        exclude = {R2dbcAutoConfiguration.class,
                    FlywayAutoConfiguration.class,
                    DataSourceAutoConfiguration.class})
public class BookRestControllerTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepositoryCustom bookRepositoryCustom;

    @LocalServerPort
    private int port;

    @Test
    void shouldReturnAllBooks() {

        var client = WebClient.create(String.format("http://localhost:%d", port));
        var expectedBookCount = 2;

        var books = getBooks();
        when(bookService.findAll()).thenReturn(Flux.fromStream(books.stream()));

        var result = client.get()
                .uri("/api/v1/books")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BookDto.class)
                .take(expectedBookCount)
                .timeout(Duration.ofSeconds(3))
                .collectList()
                .block();

        assertEquals(expectedBookCount, result.size());
    }

    @Test
    void shouldReturnRequestedBook() {

        var client = WebClient.create(String.format("http://localhost:%d", port));

        var books = getBooks();
        when(bookService.findById(2)).thenReturn(
                Mono.just(
                        books.stream()
                                .filter(b -> b.getId() == 2)
                                .findFirst().get()));

        var result = client.get().uri("/api/v1/books/2")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BookDto.class)
                .timeout(Duration.ofSeconds(3))
                .block();


        assertEquals(2, result.getId());
        assertEquals("Book2", result.getTitle());
        assertEquals("Author2", result.getAuthor());
        assertEquals("Genre2", result.getGenre());

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
