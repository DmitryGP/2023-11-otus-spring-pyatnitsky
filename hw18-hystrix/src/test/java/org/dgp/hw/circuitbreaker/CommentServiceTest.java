package org.dgp.hw.circuitbreaker;

import org.dgp.hw.repositories.CommentRepository;
import org.dgp.hw.services.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dgp.hw.circuitbreaker.Constants.NA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentServiceTest {
    @MockBean
    private CommentRepository repository;

    @Autowired
    private CommentService commentService;

    @Test
    void shouldCallFallbackMethodIfFindByIdIsFailed() {
        when(repository.findById(any(Long.class))).thenThrow(RuntimeException.class);

        var result = commentService.findById(11);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(11);
        assertThat(result.get().getText()).isEqualTo(NA);
        assertThat(result.get().getBook().getId()).isEqualTo(-1);
        assertThat(result.get().getBook().getTitle()).isEqualTo(NA);
        assertThat(result.get().getBook().getAuthor().getId()).isEqualTo(-1);
        assertThat(result.get().getBook().getAuthor().getFullName()).isEqualTo(NA);
        assertThat(result.get().getBook().getGenre().getId()).isEqualTo(-1);
        assertThat(result.get().getBook().getGenre().getName()).isEqualTo(NA);
    }

    @Test
    void shouldCallFallbackMethodIfFindByBookIdIsFailed() {
        when(repository.findByBookId(any(Long.class))).thenThrow(RuntimeException.class);

        var result = commentService.findByBookId(12);

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(-1);
        assertThat(result.get(0).getText()).isEqualTo(NA);
        assertThat(result.get(0).getBook().getId()).isEqualTo(12);
        assertThat(result.get(0).getBook().getTitle()).isEqualTo(NA);
        assertThat(result.get(0).getBook().getAuthor().getId()).isEqualTo(-1);
        assertThat(result.get(0).getBook().getAuthor().getFullName()).isEqualTo(NA);
        assertThat(result.get(0).getBook().getGenre().getId()).isEqualTo(-1);
        assertThat(result.get(0).getBook().getGenre().getName()).isEqualTo(NA);
    }
}
