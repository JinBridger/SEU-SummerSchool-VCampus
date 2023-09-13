package app.vcampus.server.entity;

import app.vcampus.server.enums.BookStatus;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * library book entity class
 */

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

    @Column(nullable = false, columnDefinition = "TEXT")
    public String description;

    @Column(nullable = false)
    public String place;

    public String cover;

    @Column(nullable = false, name = "call_number")
    public String callNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BookStatus bookStatus = BookStatus.available;


    /**
     * fromWeb method, parse book from web by its ISBN
     *
     * @param data the parsed book data
     * @return  the parsed book object
     */
    public static LibraryBook fromWeb(Map<String, Object> data) {
        try {
            LibraryBook book = new LibraryBook();
            book.setName((String) data.get("bookName"));
            book.setIsbn((String) data.get("isbn"));
            book.setAuthor((String) data.get("author"));
            book.setPress((String) data.get("press"));
            book.setDescription((String) data.get("bookDesc"));
            try {
                List<String> pictures = gson.fromJson((String) data.get("pictures"), new TypeToken<List<String>>() {
                }.getType());
                book.setCover(pictures.get(0));
            } catch (Exception e) {
                log.warn("Failed to parse cover", e);
                e.printStackTrace();
            }
            return book;
        } catch (Exception e) {
            log.warn("Failed to parse book from web", e);
            return null;
        }
    }

}
