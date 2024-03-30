package org.dgp.hw.models;

import com.google.common.base.Strings;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    private Author author;

    private Genre genre;

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(o.getClass() != Book.class) {
            return false;
        }

        var another = (Book) o;

        if(Strings.isNullOrEmpty(another.id))
        {
            return false;
        }

        return Objects.equals(this.id, another.id);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(title)
                .append(author)
                .append(genre)
                .build();
    }

}
