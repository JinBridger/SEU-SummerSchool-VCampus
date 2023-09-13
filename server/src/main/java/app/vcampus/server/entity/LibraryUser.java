package app.vcampus.server.entity;

import app.vcampus.server.enums.LibraryUserStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


/**
 * Library user entity class, however not used
 */
@Entity
@Slf4j
@Data
@Table(name = "LibraryUser")
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

    /**
     * The Map object conatins key-value pairs that represent the properties of user.
     * @param data data of library user
     * @return  return a user from the map.
     */
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

    /**
     * The Map object contains key-value pairs that represent the properties of library user
     *  The method initializes the Map object with the following key-value pairs:
     * @return  a Map object.
     */
    public Map<String, String> toMap() {
        return Map.ofEntries(
                Map.entry("cardNumber", getCardNumber().toString()),
                Map.entry("status", getStatus().toString()),
                Map.entry("borrowed", getBorrowed().toString()),
                Map.entry("maxBorrow", getMaxBorrow().toString())
        );
    }
}
