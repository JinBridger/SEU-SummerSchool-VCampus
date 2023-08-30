package app.vcampus.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Table(name = "course")
@Slf4j
public class Course {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public Integer courseId;

    @Column(nullable = false)
    public String courseName;

    @Column(nullable = false)
    public Integer school;//School offered

    public float credit;//credit

    public static Course fromMap(Map<String,String> data)
    {
        try {
            Course course=new Course();
            course.setUuid(UUID.fromString(data.get("uuid")));
            course.setCourseId(Integer.parseInt(data.get("courseId")));
            course.setCourseName(data.get("courseName"));
            course.setSchool(Integer.parseInt("school"));
            course.setCredit(Float.parseFloat(data.get("credit")));
            return course;
        }
        catch(Exception e) {
            log.warn("Failed to parse from map: {}",data,e);
            return null;
        }

    }

    public Map<String,String> toMap() {
        return Map.ofEntries(
                Map.entry("uuid",getUuid().toString()),
                Map.entry("courseId",getCourseId().toString()),
                Map.entry("courseName",getCourseName()),
                Map.entry("school",getSchool().toString()),
                Map.entry("credit", Float.toString(getCredit()))
                );
    }

}
