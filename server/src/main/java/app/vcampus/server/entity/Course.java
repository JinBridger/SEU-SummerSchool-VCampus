package app.vcampus.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Data
@Table(name = "course")
@Slf4j
public class Course {
    @Id
    public UUID uuid;

    public String courseId;

    @Column(nullable = false)
    public String courseName;

    @Column(nullable = false)
    public Integer school;//School offered

    public float credit;//credit

}
