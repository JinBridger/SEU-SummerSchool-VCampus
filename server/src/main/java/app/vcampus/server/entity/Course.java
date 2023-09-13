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

    /**
     * The Map object conatins key-value pairs that represent the properties of course.
     * @param data data of course
     * @return  return a course from the map.
     */
    public static Course fromMap(Map<String, String> data) {
        try {
            Course course = new Course();
            course.setUuid(UUID.fromString(data.get("uuid")));
            course.setCourseId(data.get("courseId"));
            course.setCourseName(data.get("courseName"));
            course.setSchool(data.get("school"));
            course.setCredit(Float.parseFloat(data.get("credit")));
            return course;
        } catch (Exception e) {
            log.warn("Failed to parse from map: {}", data, e);
            return null;
        }

    }


    /**
     * The Map object contains key-value pairs that represent the properties of course
     *  The method initializes the Map object with the following key-value pairs:
     * @return  a Map object.
     */
    public Map<String, String> toMap() {
        return Map.ofEntries(
                Map.entry("uuid", getUuid().toString()),
                Map.entry("courseId", getCourseId()),
                Map.entry("courseName", getCourseName()),
                Map.entry("school", getSchool()),
                Map.entry("credit", Float.toString(getCredit()))
        );
    }

}

