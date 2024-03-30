package org.dgp.hw.models;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    private String text;

    @DocumentReference
    private Book book;

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(o.getClass() != Author.class) {
            return false;
        }

        var another = (Comment) o;

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
                .append(text)
                .build();
    }
}
