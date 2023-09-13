package app.vcampus.server.entity;


import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.TextUtility;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

/**
 * TeachingClass class.
 */
@Entity
@Data
@Slf4j
@Table(name = "class")
public class TeachingClass implements IEntity {
    @Id
    public UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    public UUID courseUuid;

    @Transient
    public Course course;

    @Transient
    public SelectRecord selectRecord;

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

    @Transient
    public Integer selectedCount;

    @Transient
    public Boolean isEvaluated;

    @Transient
    public Pair<List<List<Integer>>, List<String>> evaluationResult;

    public String humanReadableSchedule() {
        StringBuilder content = new StringBuilder();
        String currentSeparator = "";
        for (Pair<Pair<Integer, Integer>, Pair<Integer, Pair<Integer, Integer>>> pair : schedule) {
            content.append(currentSeparator);
            content.append(pair.getFirst().getFirst()).append("-").append(pair.getFirst().getSecond()).append(" 周，");
            content.append("周").append(TextUtility.intToChineseWeek(pair.getSecond().getFirst())).append(" ");
            content.append(pair.getSecond().getSecond().getFirst()).append("-").append(pair.getSecond().getSecond().getSecond()).append(" 节");
            currentSeparator = "\n";
        }

        return content.toString();
    }
}
