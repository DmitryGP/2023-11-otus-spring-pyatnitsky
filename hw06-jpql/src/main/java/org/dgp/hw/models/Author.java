package org.dgp.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authors")
@Getter
@Setter
@EqualsAndHashCode
public class Author {

    @Id
    private long id;

    @Column(name = "full_name")
    private String fullName;
}
