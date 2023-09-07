package app.vcampus.server.entity;

import app.vcampus.server.utility.DateUtility;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Slf4j
@Table(name = "selected_class")
public class SelectedClass implements IEntity {
    @Id
    public UUID uuid;

    public UUID classUuid;

    @Column(nullable = false)
    public Integer cardNumber;

    @JdbcTypeCode(SqlTypes.JSON)
    public Grades grade;

    public Date selectTime;
//
//    public static SelectedClass fromMap(Map<String, String> data) {
//        try {
//            SelectedClass sclass = new SelectedClass();
//            sclass.setUuid(UUID.fromString(data.get("uuid")));
//            sclass.setClassUuid(UUID.fromString(data.get("classUuid")));
//            sclass.setCardNumber(Integer.parseInt(data.get("cardNumber")));
//            sclass.setSelectTime(DateUtility.toDate(data.get("selectDate")));
//            sclass.setGrade(Integer.parseInt(data.get("grade")));
//            return sclass;
//        } catch (Exception e) {
//            log.warn("Faild to parse selectedClass from map:{}", data, e);
//            return null;
//        }
//    }
//
//    public Map<String, String> toMap() {
//        return Map.ofEntries(
//                Map.entry("uuid", getUuid().toString()),
//                Map.entry("classUuid", getClassUuid().toString()),
//                Map.entry("cardNumber", getCardNumber().toString()),
//                Map.entry("selectedDate", getSelectTime().toString()),
//                Map.entry("grade", getGrade().toString())
//        );
//    }
}
