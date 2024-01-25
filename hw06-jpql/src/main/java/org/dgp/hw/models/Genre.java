package org.dgp.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "genres")
@EqualsAndHashCode
public class Genre {

    @Id
    private long id;

    @Column(name = "name")
    private String name;
}
