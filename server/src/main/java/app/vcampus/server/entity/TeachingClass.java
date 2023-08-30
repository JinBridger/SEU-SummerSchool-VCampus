package app.vcampus.server.entity;


import app.vcampus.server.utility.Pair;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Slf4j
@Table(name = "class")
public class TeachingClass implements IEntity {
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
    // [((start_week, end_week), (weekday, (start_time, end_time)))))]
    public List<Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>>> schedule;

    @Column(nullable = false)
    public String place;

    @Column(nullable = false)
    public Integer capacity;

    public static TeachingClass fromMap(Map<String, String> data) {
        Type type = new TypeToken<List<Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>>>>() {
        }.getType();

        try {
            TeachingClass teachingClass = new TeachingClass();
            teachingClass.setUuid(UUID.fromString(data.get("uuid")));
            teachingClass.setCourseUuid(UUID.fromString(data.get("courseUuid")));
            teachingClass.setCourseName(data.get("courseName"));
            teachingClass.setTeacherId(Integer.parseInt(data.get("teacherId")));
            teachingClass.setSchedule((new Gson()).fromJson(data.get("schedule"), type));
            teachingClass.setPlace(data.get("place"));
            teachingClass.setCapacity(Integer.parseInt(data.get("capacity")));

            return teachingClass;
        } catch (Exception e) {
            log.warn("Failed to parse teaching class from map: {}", data, e);
            return null;
        }
    }

    public Map<String, String> toMap() {
        return Map.of(
                "uuid", uuid.toString(),
                "courseUuid", courseUuid.toString(),
                "courseName", courseName,
                "teacherId", teacherId.toString(),
                "schedule", (new Gson()).toJson(schedule),
                "place", place,
                "capacity", capacity.toString()
        );
    }
}
