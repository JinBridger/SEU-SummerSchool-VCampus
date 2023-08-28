package app.vcampus.server.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "course")
@Slf4j
public class Course {
    @Id
    @Column(name = "courseId")
    public Integer courseID;
    @Column(nullable = false)
    public String courseName;
    @Column(nullable = false)
    public String school;//School offered
    public float credit;//credit

}
