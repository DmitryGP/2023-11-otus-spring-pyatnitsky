package org.dgp.hw.models;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-graph", attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @OneToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
