package org.dgp.hw.circuitbreaker;

import org.dgp.hw.repositories.GenreRepository;
import org.dgp.hw.services.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dgp.hw.circuitbreaker.Constants.NA;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GenreServiceTest {
    @MockBean
    private GenreRepository repository;

    @Autowired
    private GenreService genreService;

    @Test
    void shouldCallFallbackMethodIfFindAllIsFailed() {
        when(repository.findAll()).thenThrow(RuntimeException.class);

        var result = genreService.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(-1);
        assertThat(result.get(0).getName()).isEqualTo(NA);
    }
}
