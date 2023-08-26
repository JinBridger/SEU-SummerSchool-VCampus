package app.vcampus.server.controller;

import app.vcampus.server.entity.Student;
import app.vcampus.server.entity.User;
import app.vcampus.server.enums.Status;
import app.vcampus.server.enums.Polistat;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Session;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

@Slf4j
public class StudentStatusController {
    @RouteMapping(uri = "student/updateInfo")
    public Response updateInfo(Request request, org.hibernate.Session database) {
        String cardNumber = request.getParams().get("cardNumber");
        String studentNumber = request.getParams().get("studentNumber");
        Integer major = Integer.valueOf(request.getParams().get("major"));
        Integer school = Integer.valueOf(request.getParams().get("school"));
        Status  status = Status.valueOf(request.getParams().get("status"));
        String birthPlace = request.getParams().get("birthPlace");
        Polistat polistat = Polistat.valueOf(request.getParams().get("polistat"));

        if(cardNumber == null){
            return Response.Common.error("Incorrect card number");
        }

        Student student = database.get(Student.class,Integer.parseInt(cardNumber));

        Transaction tx = database.beginTransaction();
        student.setStudentNumber(studentNumber);
        student.setMajor(major);
        student.setSchool(school);
        student.setStatus(status);
        student.setBirthPlace(birthPlace);
        student.setPolistat(polistat);
        tx.commit();
        Response response = Response.Common.ok();
        return response;
    }

    @RouteMapping(uri = "student/addInfo")
    public Response addInfo(Request request, org.hibernate.Session database){
        String cardNumber = request.getParams().get("cardNumber");
        String studentNumber = request.getParams().get("studentNumber");
//        Integer major = Integer.valueOf(request.getParams().get("major"));
//        Integer school = Integer.valueOf(request.getParams().get("school"));
//        Status  status = Status.valueOf(request.getParams().get("status"));
        String birthPlace = request.getParams().get("birthPlace");
//        Polistat polistat = Polistat.valueOf(request.getParams().get("polistat"));

        if (cardNumber == null || studentNumber == null) {
            return Response.Common.badRequest();
        }

        System.out.println(Integer.valueOf(cardNumber));
        Student student = new Student();
        Transaction tx = database.beginTransaction();
        student.setCardNumber(Integer.valueOf(cardNumber));
        student.setStudentNumber(studentNumber);
//        student.setMajor(major);
//        student.setSchool(school);
//        student.setStatus(status);
        student.setBirthPlace(birthPlace);
//        student.setPolistat(polistat);
        database.persist(student);
        tx.commit();
        return Response.Common.ok();
    }

    @RouteMapping(uri = "student/Student", role = "admin")
    public Response test(Request request, org.hibernate.Session database) {
        return Response.Common.ok();
    }
}

