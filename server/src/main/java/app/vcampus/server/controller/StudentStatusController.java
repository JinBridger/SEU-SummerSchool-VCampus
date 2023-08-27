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
    /*
    Solve client to update student status information and update to the database
    The constraints are cardNumber  != null and when no such student throw exception
    Test passed on 2023/08/27
    Test : {"uri":"student/updateInfo","params":{"cardNumber":"1000","studentNumber":"15","major":"2","school":"2"}}
    */
    @RouteMapping(uri = "student/updateInfo")
    public Response updateInfo(Request request, org.hibernate.Session database) {
        String cardNumber = request.getParams().get("cardNumber");
        String studentNumber = request.getParams().get("studentNumber");
        String major = request.getParams().get("major");
        String school = request.getParams().get("school");
        String birthPlace = request.getParams().get("birthPlace");
//      Status  status = Status.valueOf(request.getParams().get("status"));
//      Polistat polistat = Polistat.valueOf(request.getParams().get("polistat"));

        if(cardNumber == null){
            return Response.Common.error("Incorrect card number");
        }

        Student student = database.get(Student.class,Integer.parseInt(cardNumber));

        if(student == null){
            return Response.Common.error("Incorrect card number");
        }
        Transaction tx = database.beginTransaction();
        student.setCardNumber(Integer.valueOf(cardNumber));
        student.setStudentNumber(studentNumber);
        student.setMajor(Integer.valueOf(major));
        student.setSchool(Integer.valueOf(school));
        student.setBirthPlace(birthPlace);
        database.persist(student);
        tx.commit();
        return Response.Common.ok();
    }

    /*
    Solve client to add student status information and update to the database
    The constraints are cardNumber and studentNumber != null
    Test passed on 2023/08/27
    Test : {"uri":"student/addInfo","params":{"cardNumber":"999","studentNumber":"15","birthPlace":"SEU"}}
     */
    @RouteMapping(uri = "student/addInfo")
    public Response addInfo(Request request, org.hibernate.Session database){
        String cardNumber = request.getParams().get("cardNumber");
        String studentNumber = request.getParams().get("studentNumber");
        String major = request.getParams().get("major");
        String school = request.getParams().get("school");
        String birthPlace = request.getParams().get("birthPlace");
//        Status  status = Status.valueOf(request.getParams().get("status"));
//        Polistat polistat = Polistat.valueOf(request.getParams().get("polistat"));

        if (cardNumber == null || studentNumber == null) {
            return Response.Common.error("card number and student number cannot be empty");
        }

        Student student = new Student();
        Transaction tx = database.beginTransaction();
        student.setCardNumber(Integer.valueOf(cardNumber));
        student.setStudentNumber(studentNumber);
        student.setMajor(Integer.valueOf(major));
        student.setSchool(Integer.valueOf(school));
        student.setBirthPlace(birthPlace);
        database.persist(student);
        tx.commit();
        return Response.Common.ok();
    }

    /*
    Solve client to delete student status information and update to the database
    The constraints : cardNumber  != null  && database have the student's cardNumber
    Test passed on 2023/08/27
    */
    @RouteMapping(uri = "student/deleteInfo")
    public Response deleteInfo(Request request, org.hibernate.Session database){
        String cardNumber = request.getParams().get("cardNumber");

        if (cardNumber == null) {
            return Response.Common.error("card number cannot be empty");
        }

        Student student = database.get(Student.class,Integer.parseInt(cardNumber));

        if(student == null){
            return Response.Common.error("no such card number");
        }

        Transaction tx = database.beginTransaction();
        database.delete(student);
        tx.commit();
        return Response.Common.ok();
    }


    /*
    Solve client to search student status information and update to the database
    The constraints : cardNumber  != null  && database have the student's cardNumber
    Test passed on 2023/08/27
    Test:{"uri":"student/searchInfo","params":{"cardNumber":"250"}}
    */
    @RouteMapping(uri = "student/searchInfo")
    public Response searchInfo(Request request, org.hibernate.Session database){
        String cardNumber = request.getParams().get("cardNumber");

        if (cardNumber == null) {
            return Response.Common.error("card number cannot be empty");
        }

        Student student = database.get(Student.class,Integer.parseInt(cardNumber));

        if(student == null){
            return Response.Common.error("no such card number");
        }

        System.out.println(student.getCardNumber());
        System.out.println(student.getStudentNumber());
        System.out.println(student.getBirthPlace());
        System.out.println(student.getSchool());
        System.out.println(student.getMajor());
        return Response.Common.ok();
    }
}