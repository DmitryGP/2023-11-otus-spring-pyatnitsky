package org.dgp.hw.circuitbreaker;

import org.dgp.hw.repositories.AuthorRepository;
import org.dgp.hw.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dgp.hw.circuitbreaker.Constants.NA;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthorServiceTest {
    @MockBean
    private AuthorRepository repository;

    @Autowired
    private AuthorService authorService;

    @Test
    void shouldCallFallbackMethodIfFindAllIsFailed() {
        when(repository.findAll()).thenThrow(RuntimeException.class);

        var result = authorService.findAll();

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo(-1);
        assertThat(result.get(0).getFullName()).isEqualTo(NA);
    }
}
