package app.vcampus.server.entity;

import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.Status;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Entity
@Data
@Table(name = "student")
@Slf4j
public class Student {
    @Id
    @Column(name = "cardNumber")
    public Integer cardNumber;

    @Column(nullable = false)
    public String studentNumber;

    public Integer major;

    public Integer school;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Status status;

    public String birthPlace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PoliticalStatus politicalStatus;

    public static Student fromRequest(Request request) {
        try {
            String cardNumber = request.getParams().get("cardNumber");
            String studentNumber = request.getParams().get("studentNumber");
            String major = request.getParams().get("major");
            String school = request.getParams().get("school");
            String birthPlace = request.getParams().get("birthPlace");
            Status status = Status.valueOf(request.getParams().get("status"));
            PoliticalStatus politicalStatus = PoliticalStatus.valueOf(request.getParams().get("politicalStatus"));

            Student student = new Student();
            student.setCardNumber(Integer.valueOf(cardNumber));
            student.setStudentNumber(studentNumber);
            student.setMajor(Integer.valueOf(major));
            student.setSchool(Integer.valueOf(school));
            student.setBirthPlace(birthPlace);
            student.setStatus(status);
            student.setPoliticalStatus(politicalStatus);
            return student;
        } catch (Exception e) {
            log.warn("Failed to parse student from request", e);
            return null;
        }
    }

    public Response toResponse() {
        Response response = Response.Common.ok();
        response.setData(Map.of(
            "cardNumber", cardNumber,
            "studentNumber", studentNumber,
            "major", major,
            "school", school,
            "birthPlace", birthPlace,
            "status", status,
            "politicalStatus", politicalStatus
        ));
        return response;
    }
}
