package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class TeachingAffairsController {
    @RouteMapping(uri = "course/addCourse")
    public Response addCourse(Request request, org.hibernate.Session database) {
        Course newCourse = Course.fromMap(request.getParams());
        if (newCourse == null) {
            return Response.Common.badRequest();
        }

        Course user = database.get(Course.class, newCourse.getCourseId());
        if (user == null) {
            return Response.Common.error("Course not found");
        }

        Transaction tx = database.beginTransaction();
        database.persist(newCourse);
        tx.commit();

        return Response.Common.ok();
    }

    /*This method is used to select courses for students*/
    @RouteMapping(uri="selectedClass/selectClass")
    public Response selectclass(Request request, org.hibernate.Session database) {
        SelectedClass newSelectedClass = SelectedClass.fromMap(request.getParams());
        if (newSelectedClass == null) {
            return Response.Common.badRequest();
        }
        SelectedClass selectedClass = database.get(SelectedClass.class, newSelectedClass.getCardNumber());
        if (selectedClass == null) {
            return Response.Common.error("SelectedClass not found");
        }

        Transaction tx = database.beginTransaction();
        database.persist(newSelectedClass);
        tx.commit();
        return Response.Common.ok();
    }

    /*for student to search selectedClass information*/
    @RouteMapping(uri="selectedClass/searchInfo")
    public Response searchInfo(Request request,org.hibernate.Session database){
        String cardNumber=request.getParams().get("cardNumber");

        if(cardNumber==null){
            return Response.Common.error("card number cannot be empty");
        }

        SelectedClass selectedClass=database.get(SelectedClass.class,Integer.parseInt(cardNumber));

        if(selectedClass==null){
            return Response.Common.error("no such card number");
        }
        System.out.println(selectedClass);
        return Response.Common.ok(selectedClass.toMap());
    }

    /*for teacher to search teaching class information*/
    @RouteMapping(uri="class/searchClass")
    public Response searchClass(Request request,org.hibernate.Session database){
        String cardNumber=request.getParams().get("cardNumber");

        if(cardNumber==null){
            return Response.Common.error("card number cannot be empty");
        }

        TeachingClass teachingClass=database.get(TeachingClass.class,Integer.parseInt(cardNumber));
        if(teachingClass==null){
            return Response.Common.error("no such card number");
        }

        System.out.println(teachingClass);

        return Response.Common.ok(teachingClass.toMap());
    }


    @RouteMapping(uri = "selectedClass/recordGrade", role = "teachingAffairs")
    public Response recordGrade(Request request, org.hibernate.Session database) {
        String uuidString = request.getParams().get("uuid");
        String gradeString = request.getParams().get("grade");

        if (uuidString == null || gradeString == null) {
            return Response.Common.badRequest();
        }

        try {
            UUID uuid = UUID.fromString(uuidString);
            Integer grade = Integer.parseInt(gradeString);

            SelectedClass selectedClass = database.get(SelectedClass.class, uuid);
            if (selectedClass == null) {
                return Response.Common.error("SelectedClass not found");
            }

            Transaction tx = database.beginTransaction();
            selectedClass.setGrade(grade);
            database.persist(selectedClass);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to parse UUID or grade", e);
            return Response.Common.badRequest();
        }
    }


}
