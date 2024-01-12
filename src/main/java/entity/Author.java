package entity;

import base.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Author extends BaseEntity<Long> {
    String firstName;
    String lastName;
    String userName;
    String password;
    String email;
    @Temporal(TemporalType.DATE)
    LocalDate brithDate;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Embedded
    Address address;
    @OneToMany(mappedBy = "author")
    Set<Book> books = new HashSet<>();

    public Author(String firstName, String lastName, String userName, String password, String email, LocalDate brithDate, Gender gender, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.brithDate = brithDate;
        this.gender = gender;
        this.address = address;
    }
}