package org.dgp.hw.models;

import com.google.common.base.Strings;
import lombok.Builder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    private String id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if(o == null) {
            return false;
        }

        if(o.getClass() != Genre.class) {
            return false;
        }

        var another = (Genre) o;

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
                .append(name)
                .build();
    }
}
