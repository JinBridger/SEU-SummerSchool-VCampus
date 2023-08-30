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
@Slf4j
@Table(name="class")
public class Class {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public Integer courseId;

    @Column(nullable = false)
    public String courseName;

    @Column(nullable = false)
    public Integer teacherId;

    /*@Column(nullable = false)
    public schedule  */

    @Column(nullable = false)
    public String place;

    @Column(nullable = false)
    public Integer  capacity;

    
}
