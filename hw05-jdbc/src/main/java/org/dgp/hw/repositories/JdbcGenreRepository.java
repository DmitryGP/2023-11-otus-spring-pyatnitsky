package org.dgp.hw.repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.dgp.hw.models.Genre;
import org.springframework.util.CollectionUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> findAll() {

        return jdbcOperations.query("select id, name from genres", new GenreRowMapper());
    }

    @Override
    public Optional<Genre> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        List<Genre> result = jdbcOperations.query("select id, name from genres where id = :id",
                                                    params, new GenreRowMapper());

        if (CollectionUtils.isEmpty(result)) {
            return Optional.empty();
        }

        return Optional.of(result.getFirst());

    }

    private static class GenreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");

            return new Genre(id, name);
        }
    }
}
