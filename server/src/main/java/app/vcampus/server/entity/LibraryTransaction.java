package app.vcampus.server.entity;

import app.vcampus.server.enums.BookStatus;
import app.vcampus.server.enums.LibraryAction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;
import java.time.LocalDateTime;


@Entity
@Data
@Slf4j
@Table(name = "bookTransaction")
public class LibraryTransaction {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID bookUuid;

    @Column(nullable = false)
    public Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public LibraryAction action;

    @Column(nullable = false)
    public LocalDateTime time;


    public static LibraryTransaction fromMap(Map<String,String>data){
        try {
            LibraryTransaction tx=new LibraryTransaction();
            tx.setUuid(UUID.fromString(data.get("UUID")));
            tx.setBookUuid(UUID.fromString(data.get("bookUuid")));
            tx.setAction(LibraryAction.valueOf(data.get("action")));
            tx.setUserId(Integer.parseInt(data.get("userId")));
            tx.setTime(LocalDateTime.parse(data.get("time")));
            return tx;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,String> toMap(){
        return Map.ofEntries(
                Map.entry("UUID",getUuid().toString()),
                Map.entry("bookUuid",getBookUuid().toString()),
                Map.entry("userId",getUserId().toString()),
                Map.entry("action",getAction().toString()),
                Map.entry("time",getTime().toString())
        );
    }

}
