package app.vcampus.server.entity;

import app.vcampus.server.enums.Gender;
import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.Status;
import app.vcampus.server.utility.DateUtility;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Entity
@Data
@Table(name = "student")
@Slf4j
public class Student implements IEntity {
    @Id
    @Column(name = "cardNumber")
    public Integer cardNumber;

    @Column(nullable = false)
    public String studentNumber;

    @Column(nullable = false)
    public String familyName;

    @Column(nullable = false)
    public String givenName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Gender gender;

    public Date birthDate;

    public String major;

    public String school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Status status;

    public String birthPlace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PoliticalStatus politicalStatus;

    public static Student fromMap(Map<String, String> data) {
        try {
            Student student = new Student();
            student.setCardNumber(Integer.parseInt(data.get("cardNumber")));
            student.setStudentNumber(data.get("studentNumber"));
            student.setMajor(data.get("major"));
            student.setSchool(data.get("school"));
            student.setBirthPlace(data.get("birthPlace"));
            student.setStatus(Status.valueOf(data.get("status")));
            student.setPoliticalStatus(PoliticalStatus.valueOf(data.get("politicalStatus")));
            student.setGivenName(data.get("givenName"));
            student.setFamilyName(data.get("familyName"));
            student.setBirthDate(DateUtility.toDate(data.get("birthDate")));
            student.setGender(Gender.valueOf(data.get("gender")));
            return student;
        } catch (Exception e) {
            log.warn("Failed to parse student from map: {}", data, e);
            return null;
        }
    }

    public Map<String, String> toMap() {
        return Map.ofEntries(
                Map.entry("cardNumber", getCardNumber().toString()),
                Map.entry("studentNumber", getStudentNumber()),
                Map.entry("major", getMajor()),
                Map.entry("school", getSchool()),
                Map.entry("birthPlace", getBirthPlace()),
                Map.entry("status", getStatus().toString()),
                Map.entry("politicalStatus", getPoliticalStatus().toString()),
                Map.entry("givenName", getGivenName()),
                Map.entry("familyName", getFamilyName()),
                Map.entry("birthDate", DateUtility.fromDate(getBirthDate())),
                Map.entry("gender", getGender().toString())
        );
    }
}
