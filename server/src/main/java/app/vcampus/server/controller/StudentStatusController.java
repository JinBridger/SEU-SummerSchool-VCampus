package app.vcampus.server.controller;

import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.Student;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class StudentStatusController {
    /*
    Solve client to update student status information and update to the database
    The constraints are cardNumber  != null and when no such student throw exception
    Test passed on 2023/08/27
    Test : {"uri":"student/updateInfo","params":{"cardNumber":"1000","studentNumber":"15","major":"2","school":"2"}}
    */
    @RouteMapping(uri = "student/updateInfo", role = "affairs_staff")
    public Response updateInfo(Request request, org.hibernate.Session database) {
        Student newStudent = IEntity.fromJson(request.getParams().get("student"), Student.class);

        if (newStudent == null) {
            return Response.Common.badRequest();
        }

        Transaction tx = database.beginTransaction();
        database.merge(newStudent);
        tx.commit();

        return Response.Common.ok(newStudent.toMap());
    }

    @RouteMapping(uri = "student/getSelf", role = "student")
    public Response getSelf(Request request, org.hibernate.Session database) {
        Integer cardNumber = request.getSession().getCardNum();

        Student student = database.get(Student.class, cardNumber);

        if (student == null) {
            return Response.Common.error("no such card number");
        }

        System.out.println(student.toMap());

        return Response.Common.ok(student.toMap());
    }

    @RouteMapping(uri = "student/filter", role = "affairs_staff")
    public Response filter(Request request, org.hibernate.Session database) {
        try {
            String keyword = request.getParams().get("keyword");
            List<Student> students;
            if (keyword == null) {
                students = Database.loadAllData(Student.class, database);
            } else {
                students = Database.likeQuery(Student.class, new String[]{"cardNumber", "studentNumber", "givenName", "familyName", "birthDate", "major", "school", "birthPlace"}, keyword, database);
            }

            return Response.Common.ok(Map.of("students", students.stream().map(Student::toJson).collect(Collectors.toList())));
        } catch (Exception e) {
            log.warn("Failed to filter students", e);
            return Response.Common.error("Failed to filter students");
        }
    }
}