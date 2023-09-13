package app.vcampus.server.entity;

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

/**
 * TeachingEvaluation class.
 */
@Entity
@Data
@Table(name = "teaching_evaluation")
@Slf4j
public class TeachingEvaluation implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID classUuid;

    @Column(nullable = false)
    public Integer studentId;

    @JdbcTypeCode(SqlTypes.JSON)
    public List<Integer> result;

    @Column(columnDefinition = "TEXT")
    public String comment;
}
