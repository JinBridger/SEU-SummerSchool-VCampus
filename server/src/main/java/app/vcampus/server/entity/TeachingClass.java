package app.vcampus.server.entity;


import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Text;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.persistence.*;
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

    @Transient
    public Course course;

    @Transient
    public SelectedClass selectedClass;

    @Column(nullable = false)
    public Integer teacherId;

    @Transient
    public String teacherName;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    // [((start_week, end_week), (weekday, (start_time, end_time)))))]
    public List<Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>>> schedule;

    @Column(nullable = false)
    public String place;

    @Column(nullable = false)
    public Integer capacity;

    public String humanReadableSchedule() {
        StringBuilder content = new StringBuilder();
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>> pair : schedule) {
            content.append(pair.getFirst().getFirst()).append("-").append(pair.getFirst().getSecond()).append(" 周，");
            content.append("周").append(Text.intToChineseWeek(pair.getSecond().getFirst())).append(" ");
            content.append(pair.getSecond().getSecond().getFirst()).append("-").append(pair.getSecond().getSecond().getSecond()).append(" 节");
            content.append("\n");
        }

        return content.toString();
    }
}
