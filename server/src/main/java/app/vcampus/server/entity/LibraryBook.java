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
    public UUID uuid = UUID.randomUUID();

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
    public BookStatus bookStatus = BookStatus.available;

    public static LibraryBook fromWeb(Map<String, Object> data) {
        try {
            LibraryBook book = new LibraryBook();
            book.setName((String) data.get("bookName"));
            book.setIsbn((String) data.get("isbn"));
            book.setAuthor((String) data.get("author"));
            book.setPress((String) data.get("press"));
            book.setDescription((String) data.get("bookDesc"));
            return book;
        } catch (Exception e) {
            log.warn("Failed to parse book from web", e);
            return null;
        }
    }
}
