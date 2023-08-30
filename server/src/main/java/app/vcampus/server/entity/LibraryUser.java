package app.vcampus.server.entity;

import app.vcampus.server.enums.BookStatus;
import app.vcampus.server.enums.LibraryAction;
import app.vcampus.server.enums.LibraryUserStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Slf4j
@Data
@Table(name="LibraryUser")
public class LibraryUser {
    @Id
    public Integer cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public LibraryUserStatus status;

    @Column(nullable = false)
    public Integer borrowed;

    @Column(nullable = false)
    public Integer maxBorrow;

    public static LibraryUser fromMap(Map<String, String> data) {
        try {
            LibraryUser user = new LibraryUser();
            user.setCardNumber(Integer.parseInt(data.get("cardNumber")));
            user.setStatus(LibraryUserStatus.valueOf(data.get("status")));
            user.setBorrowed(Integer.parseInt(data.get("borrowed")));
            user.setMaxBorrow(Integer.parseInt("maxBorrowed"));
            return user;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,String> toMap(){
        return Map.ofEntries(
                Map.entry("cardNumber",getCardNumber().toString()),
                Map.entry("status",getStatus().toString()),
                Map.entry("borrowed",getBorrowed().toString()),
                Map.entry("maxBorrow",getMaxBorrow().toString())
        );
    }
}
