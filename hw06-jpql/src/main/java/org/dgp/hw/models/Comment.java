package org.dgp.hw.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
@EqualsAndHashCode()
@NamedEntityGraph(name = "comment-graph", attributeNodes = {@NamedAttributeNode("book")})
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "text")
    private String text;

    @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @EqualsAndHashCode.Exclude
    private Book book;

    @Override
    public String toString() {
        return "Comment id = %d [%s] for book id = %d".formatted(Id, text, book != null ? book.getId() : 0);
    }
}
