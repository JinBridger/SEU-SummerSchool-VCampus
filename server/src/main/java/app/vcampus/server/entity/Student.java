package app.vcampus.server.entity;

import app.vcampus.server.enums.Gender;
import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.StudentStatus;
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
    public StudentStatus status;

    public String birthPlace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PoliticalStatus politicalStatus;


    /*
    Used by the administrator to add a new student
     */
    public static Student getStudent(User user) {
        Student student = new Student();
        student.setCardNumber(user.getCardNum());
        student.setStudentNumber("");
        student.setFamilyName("");
        student.setGivenName("");
        student.setGender(user.getGender());
        student.setBirthDate(null);
        student.setMajor("");
        student.setSchool("");
        student.setStatus(StudentStatus.inSchool);
        student.setBirthPlace("");
        student.setPoliticalStatus(PoliticalStatus.Masses);
        return student;
    }

    /*
    creates a new Student object from a Map object.
    The Map object contains key-value pairs that represent the properties of the Student object.
     */
    public static Student fromMap(Map<String, String> data) {
        try {
            Student student = new Student();
            student.setCardNumber(Integer.parseInt(data.get("cardNumber")));
            student.setStudentNumber(data.get("studentNumber"));
            student.setMajor(data.get("major"));
            student.setSchool(data.get("school"));
            student.setBirthPlace(data.get("birthPlace"));
            student.setStatus(StudentStatus.valueOf(data.get("status")));
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
    /*
    returns a map object from a Map object.
    The method initializes the Map object with the following key-value pairs:
    The Map object contains key-value pairs that represent the properties of the Student object.
    */
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

