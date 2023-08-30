package app.vcampus.server.entity;

import app.vcampus.server.enums.BookStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Slf4j
@Table(name = "book")
public class LibraryBook implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String isbn;

    @Column(nullable = false)
    public String author;

    @Column(nullable = false)
    public String press;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public String place;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BookStatus bookStatus;

    public static LibraryBook fromMap(Map<String, String> data) {
        try {
            String uuid = data.get("UUID");
            String name = data.get("name");
            String ISBN = data.get("ISBN");
            String author = data.get("author");
            String press = data.get("press");
            String description = data.get("description");
            String place = data.get("place");
            BookStatus bookStatus = BookStatus.valueOf(data.get("bookStatus"));

            LibraryBook book = new LibraryBook();
            book.setUuid(UUID.fromString(uuid));
            book.setName(name);
            book.setIsbn(ISBN);
            book.setAuthor(author);
            book.setPress(press);
            book.setDescription(description);
            book.setPlace(place);
            book.setBookStatus(bookStatus);
            return book;

        } catch (Exception e) {
            log.warn("Failed to parse student from request", e);
            return null;
        }
    }

    public Map<String, String> toMap() {
        return Map.of(
                "UUID", getUuid().toString(),
                "name", getName(),
                "ISBN", getIsbn(),
                "author", getAuthor(),
                "press", getPress(),
                "description", getDescription(),
                "place", getPlace(),
                "bookStatus", getBookStatus().toString()
        );
    }
}
