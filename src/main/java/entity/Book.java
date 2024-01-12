package entity;

import base.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book extends BaseEntity<Long> {
    String title;
    String isbn;
    @ManyToOne
    @JoinColumn(name = "author_id",referencedColumnName = "id")
    Author author;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }
}