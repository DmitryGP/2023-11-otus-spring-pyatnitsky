package org.dgp.hw.repositories;

import org.dgp.hw.security.modeles.User;
import org.dgp.hw.security.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с пользователями ")
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен загружать пользователя по имени")
    @ParameterizedTest
    @MethodSource("getDbUsers")
    void shouldReturnCorrectUserByName(Long userId, String username) {

        var actualUser = repository.findByUsername(username);

        var expectedUser = em.find(User.class, userId);

        assertThat(actualUser).isPresent()
                .get()
                .isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("должен возвращать пустой Optional, если пользователя с таким имененм не существует")
    void shouldReturnEmptyOptionalUser() {
        var actualUser = repository.findByUsername("notuser");

        assertThat(actualUser).isEmpty();
    }

    public static Stream<Arguments> getDbUsers() {
        Map<Long, String> users = new HashMap<>();
        users.put(1L, "user1");
        users.put(2L, "user2");

        return users.entrySet().stream().map(e -> Arguments.of(e.getKey(), e.getValue()));
    }
}
