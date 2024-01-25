package org.dgp.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
@EqualsAndHashCode
@Getter
@Setter
public class Author {

    @Id
    private long id;

    @Column(name = "full_name")
    private String fullName;
}
