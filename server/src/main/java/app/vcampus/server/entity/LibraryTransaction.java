package app.vcampus.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * LibraryTransaction class, used for record library transaction
 */
@Entity
@Data
@Slf4j
@Table(name = "library_transaction")
public class LibraryTransaction implements IEntity {
    @Id
    public UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    public UUID bookUuid;

    @Column(nullable = false)
    public Integer userId;

    @Column(nullable = false)
    public Date borrowTime;

    @Column(nullable = false)
    public Date dueTime;

    public Date returnTime;

    @Transient
    public LibraryBook book;
}
