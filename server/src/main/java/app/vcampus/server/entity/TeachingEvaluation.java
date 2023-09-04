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
@Table(name = "teaching_evaluation")
@Slf4j
public class TeachingEvaluation implements IEntity {
    @Id
    public UUID uuid;

    @Column(nullable = false)
    public UUID EvaluationUUID;

    @Column(nullable = false)
    public String teachingEvaluation;

    @Column(nullable = false)
    public Integer teacherId;

    public static TeachingEvaluation fromMap(Map<String, String> data) {
        try{
            TeachingEvaluation teachingEvaluation1=new TeachingEvaluation();
            teachingEvaluation1.setUuid(UUID.fromString(data.get("uuid")));
            teachingEvaluation1.setEvaluationUUID(UUID.fromString(data.get("evaluationUUID")));
            teachingEvaluation1.setTeachingEvaluation(data.get("teachingEvaluation"));
            teachingEvaluation1.setTeacherId(Integer.parseInt(data.get("teacherId")));

            return teachingEvaluation1;
        }catch(Exception e){
            log.warn("Failed to parse evaluation from map:{}",data,e);
            return null;
        }
    }

    public Map<String, String> toMap()
    {
      return Map.ofEntries(
        Map.entry("uuid",getUuid().toString()),
        Map.entry("evaluationUUID",getEvaluationUUID().toString()),
        Map.entry("teachingEvaluation",getTeachingEvaluation()),
              Map.entry("teacherId",teacherId.toString())
        );
    }
}
