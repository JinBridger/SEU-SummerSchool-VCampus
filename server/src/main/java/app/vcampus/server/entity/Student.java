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

/**
 * Student class.
 */
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

}

