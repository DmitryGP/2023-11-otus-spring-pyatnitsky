package org.dgp.hw.circuitbreaker;

import org.dgp.hw.repositories.BookRepository;
import org.dgp.hw.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dgp.hw.circuitbreaker.Constants.NA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {

    @MockBean
    BookRepository repository;

    @Autowired
    BookService bookService;

    @Test
    void shouldCallFallbackMethodIfFindByIdIsFailed() {
        when(repository.findById(any(Long.class))).thenThrow(RuntimeException.class);

        var result = bookService.findById(10);

        assertThat(result).isNotNull();

        assertThat(result.getId()).isEqualTo(10);
        assertThat(result.getTitle()).isEqualTo(NA);
        assertThat(result.getAuthor().getId()).isEqualTo(-1);
        assertThat(result.getAuthor().getFullName()).isEqualTo(NA);
        assertThat(result.getGenre().getId()).isEqualTo(-1);
        assertThat(result.getGenre().getName()).isEqualTo(NA);
    }

    @Test
    void shouldCallFallbackMethodIfFindAllIsFailed() {
        when(repository.findAll()).thenThrow(RuntimeException.class);

        var result = bookService.findAll();

        assertThat(result.size()).isEqualTo(1);

        assertThat(result.get(0).getId()).isEqualTo(-1);
        assertThat(result.get(0).getTitle()).isEqualTo(NA);
        assertThat(result.get(0).getAuthor().getId()).isEqualTo(-1);
        assertThat(result.get(0).getAuthor().getFullName()).isEqualTo(NA);
        assertThat(result.get(0).getGenre().getId()).isEqualTo(-1);
        assertThat(result.get(0).getGenre().getName()).isEqualTo(NA);
    }
}
