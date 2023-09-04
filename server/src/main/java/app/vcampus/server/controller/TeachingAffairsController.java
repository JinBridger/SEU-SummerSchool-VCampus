package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
    public Response selectClass(Request request, org.hibernate.Session database) {
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


    @RouteMapping(uri = "student/myGrades", role = "student")
    public Response getMyGrades(Request request, org.hibernate.Session database) {
        String cardNumberString = request.getParams().get("cardNumber");

        if (cardNumberString == null)
        {
            return Response.Common.error("card number cannot be empty");
        }

        try {
            Integer cardNumber = Integer.parseInt(cardNumberString);

            TypedQuery<SelectedClass> query = database.createQuery(
                            "SELECT sc FROM SelectedClass sc WHERE sc.cardNumber = :cardNumber", SelectedClass.class)
                    .setParameter("cardNumber", cardNumber);

            List<SelectedClass> mySelectedClasses = query.getResultList();

            if (mySelectedClasses.isEmpty()) {
                return Response.Common.error("no selected classes found for this card number");
            }

            List<Map<String, String>> gradeList = new ArrayList<>();
            for (SelectedClass selectedClass : mySelectedClasses) {
                Map<String, String> gradeInfo = new HashMap<>();
                gradeInfo.put("classUuid", selectedClass.getClassUuid().toString());
                gradeInfo.put("grade", selectedClass.getGrade().toString());
                gradeList.add(gradeInfo);
            }

            return Response.Common.ok(gradeList);
        } catch (NumberFormatException e) {
            log.warn("Failed to parse card number", e);
            return Response.Common.badRequest();
        }
    }

    @RouteMapping(uri = "TeachingEvaluaiotn", role = "student")
    public Response addEvaluaiton(Request request, org.hibernate.Session database)
    {
        TeachingEvaluation newTeachingEvaluation=IEntity.fromJson(request.getParams().get("evaluation"), TeachingEvaluation.class);
        if(newTeachingEvaluation==null)
        {
            return Response.Common.badRequest();
        }
        newTeachingEvaluation.setUuid(UUID.randomUUID());
        Transaction tx=database.beginTransaction();
        database.persist(newTeachingEvaluation);
        tx.commit();
        return Response.Common.ok();
    }
}
