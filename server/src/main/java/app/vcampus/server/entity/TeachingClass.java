package app.vcampus.server.entity;


import app.vcampus.server.utility.Pair;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Slf4j
@Table(name = "class")
public class TeachingClass {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID courseUuid;

    @Column(nullable = false)
    public String courseName;

    @Column(nullable = false)
    public Integer teacherId;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    public List<Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>>> schedule;

    @Column(nullable = false)
    public String place;

    @Column(nullable = false)
    public Integer capacity;


}
