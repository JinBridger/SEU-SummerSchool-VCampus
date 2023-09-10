package app.vcampus.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Slf4j
@Table(name = "select_record")
public class SelectRecord implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID classUuid;

    @Column(nullable = false)
    public Integer cardNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    public Grades grade;

    public Date selectTime;
}
