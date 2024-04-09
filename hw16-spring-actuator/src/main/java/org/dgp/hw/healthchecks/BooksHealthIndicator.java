package org.dgp.hw.healthchecks;

import lombok.RequiredArgsConstructor;
import org.dgp.hw.services.BookService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BooksHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {

        var books = bookService.findAll();

        if (books.isEmpty()) {
            return Health.down()
                    .withDetail("message", "There is no any book.")
                    .build();
        }

        return Health.up()
                .withDetail("message", "Books are enough.")
                .build();
    }
}
