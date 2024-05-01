package org.dgp.hw.models;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "customBook",
        types = { Book.class })
public interface CustomBook {

    String getTitle();

    @Value("#{target.getAuthor().getFullName()}")
    String getAuthor();

    @Value("#{target.getGenre().getName()}")
    String getGenre();
}
