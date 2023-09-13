package app.vcampus.server.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Table(name = "course")
@Slf4j
public class Course implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public String courseId;

    @Column(nullable = false)
    public String courseName;

    @Column(nullable = false)
    public String school;

    public float credit;

    @Transient
    public List<TeachingClass> teachingClasses;

}

