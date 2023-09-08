package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TeachingAffairsController {
    @RouteMapping(uri = "teachingAffairs/student/getSelectedClasses", role = "student")
    public Response getSelectedClasses(Request request, org.hibernate.Session database) {
        Integer cardNumber = request.getSession().getCardNum();

        Student student = database.get(Student.class, cardNumber);

        if (student == null) {
            return Response.Common.error("no such card number");
        }

        List<SelectedClass> selectedClasses = Database.getWhereString(SelectedClass.class, "cardNumber", cardNumber.toString(), database);
        List<TeachingClass> teachingClasses = selectedClasses.stream().map((SelectedClass sc) -> {
            Grades grades = sc.getGrade();
            List<SelectedClass> classmates = Database.getWhereUuid(SelectedClass.class, "classUuid", sc.getClassUuid(), database);
            List<Grades> gradesList = classmates.stream().map(SelectedClass::getGrade).toList();
            grades.classAvg = gradesList.stream().mapToInt(Grades::getTotal).average().orElse(0);
            grades.classMax = gradesList.stream().mapToInt(Grades::getTotal).max().orElse(0);
            grades.classMin = gradesList.stream().mapToInt(Grades::getTotal).min().orElse(0);
            sc.setGrade(grades);

            TeachingClass teachingClass = database.get(TeachingClass.class, sc.getClassUuid());
            teachingClass.setSelectedClass(sc);
            return teachingClass;
        }).toList();
        List<TeachingClass> teachingClassesWithTeacherName = teachingClasses.stream().peek((TeachingClass tc) -> {
            User teacher = database.get(User.class, tc.getTeacherId());
            tc.setTeacherName(teacher.getName());

            Course course = database.get(Course.class, tc.getCourseUuid());
            tc.setCourse(course);
        }).toList();

        return Response.Common.ok(Map.of("classes", teachingClassesWithTeacherName.stream().map(TeachingClass::toJson).toList()));
    }

    @RouteMapping(uri = "course/addCourse", role = "affairs_staff")
    public Response addCourse(Request request, org.hibernate.Session database) {
        Course newCourse = Course.fromMap(request.getParams());
        if (newCourse == null) {
            return Response.Common.badRequest();
        }

        newCourse.setUuid(UUID.randomUUID());
        Transaction tx = database.beginTransaction();
        Database.updateWhere(Course.class, "courseId", newCourse.getCourseId(), List.of(
                new Pair<>("courseName", newCourse.getCourseName()),
//                new Pair<>("school", Integer.toString(newCourse.getSchool())),
                new Pair<>("credit", Float.toString(newCourse.getCredit()))
        ), database);
        database.persist(newCourse);
        tx.commit();

        return Response.Common.ok();
    }

    @RouteMapping(uri = "teachingAffairs/deleteCourse", role = "affairs_staff")
    public Response deleteCourse(Request request, org.hibernate.Session database) {
        String id = request.getParams().get("uuid");

        if (id == null) return Response.Common.error("Course UUID connot be empty");

        UUID uuid = UUID.fromString(id);
        Course toDelete = database.get(Course.class, uuid);
        if (toDelete == null) return Response.Common.error("No such course");

        Transaction tx = database.beginTransaction();
        database.remove(toDelete);
        tx.commit();

        return Response.Common.ok();
    }

    /*This method is used to select courses for students*/
//    @RouteMapping(uri = "selectedClass/selectClass", role = "student")
//    public Response selectClass(Request request, org.hibernate.Session database) {
//        SelectedClass newSelectedClass = SelectedClass.fromMap(request.getParams());
//        if (newSelectedClass == null) {
//            return Response.Common.badRequest();
//        }
//        SelectedClass selectedClass = database.get(SelectedClass.class, newSelectedClass.getCardNumber());
//        if (selectedClass == null) {
//            return Response.Common.error("SelectedClass not found");
//        }
//
//        Transaction tx = database.beginTransaction();
//        database.persist(newSelectedClass);
//        tx.commit();
//        return Response.Common.ok();
//    }

    /*for student to search selectedClass information*/
//    @RouteMapping(uri = "selectedClass/searchInfo", role = "student")
//    public Response searchInfo(Request request, org.hibernate.Session database) {
//        String cardNumber = request.getParams().get("cardNumber");
//
//        if (cardNumber == null) {
//            return Response.Common.error("card number cannot be empty");
//        }
//
//        SelectedClass selectedClass = database.get(SelectedClass.class, Integer.parseInt(cardNumber));
//
//        if (selectedClass == null) {
//            return Response.Common.error("no such card number");
//        }
//        System.out.println(selectedClass);
//        return Response.Common.ok(selectedClass.toMap());
//    }

    /*for teacher to search teaching class information*/
    @RouteMapping(uri = "class/searchClass", role = "teacher")
    public Response searchClass(Request request, org.hibernate.Session database) {
        String cardNumber = request.getParams().get("cardNumber");

        if (cardNumber == null) {
            return Response.Common.error("card number cannot be empty");
        }

        TeachingClass teachingClass = database.get(TeachingClass.class, Integer.parseInt(cardNumber));
        if (teachingClass == null) {
            return Response.Common.error("no such card number");
        }

        System.out.println(teachingClass);

        return Response.Common.ok(teachingClass.toMap());
    }

@RouteMapping(uri="teachingAffairs/updateCourse",role="affairs_staff")
public Response updateCourse(Request request, org.hibernate.Session database)
{
    Course newCourse=IEntity.fromJson(request.getParams().get("course"), Course.class);
    Course toUpdate=database.get(Course.class,newCourse.getUuid());
    if(toUpdate==null)
    {
        return Response.Common.badRequest();
    }

    Transaction tx=database.beginTransaction();
    toUpdate.setCourseId(newCourse.getCourseId());
    toUpdate.setCourseName(newCourse.getCourseName());
    toUpdate.setCredit(newCourse.getCredit());
    toUpdate.setSchool(newCourse.getSchool());
    database.persist(toUpdate);
    tx.commit();

    return Response.Common.ok();
}


//    @RouteMapping(uri = "selectedClass/recordGrade", role = "affairs_staff")
//    public Response recordGrade(Request request, org.hibernate.Session database) {
//        String uuidString = request.getParams().get("uuid");
//        String gradeString = request.getParams().get("grade");
//
//        if (uuidString == null || gradeString == null) {
//            return Response.Common.badRequest();
//        }
//
//        try {
//            UUID uuid = UUID.fromString(uuidString);
//            Integer grade = Integer.parseInt(gradeString);
//
//            SelectedClass selectedClass = database.get(SelectedClass.class, uuid);
//            if (selectedClass == null) {
//                return Response.Common.error("SelectedClass not found");
//            }
//
//            Transaction tx = database.beginTransaction();
//            selectedClass.setGrade(grade);
//            database.persist(selectedClass);
//            tx.commit();
//
//            return Response.Common.ok();
//        } catch (Exception e) {
//            log.warn("Failed to parse UUID or grade", e);
//            return Response.Common.badRequest();
//        }
//    }


//    @RouteMapping(uri = "selectedClass/grade", role = "student")
//    public Response searchGrades(Request request, org.hibernate.Session database) {
//
////        String cardNumberString = request.getParams().get("cardNumber");
////
////        if (cardNumberString == null)
////        {
////            return Response.Common.error("card number cannot be empty");
////        }
////
////        try {
////            Integer cardNumber = Integer.parseInt(cardNumberString);
////
////            TypedQuery<SelectedClass> query = database.createQuery(
////                            "SELECT sc FROM SelectedClass sc WHERE sc.cardNumber = :cardNumber", SelectedClass.class)
////                    .setParameter("cardNumber", cardNumber);
////
////            List<SelectedClass> mySelectedClasses = query.getResultList();
////
////            if (mySelectedClasses.isEmpty()) {
////                return Response.Common.error("no selected classes found for this card number");
////            }
////
////            List<Map<String, String>> gradeList = new ArrayList<>();
////            for (SelectedClass selectedClass : mySelectedClasses) {
////                Map<String, String> gradeInfo = new HashMap<>();
////                gradeInfo.put("classUuid", selectedClass.getClassUuid().toString());
////                gradeInfo.put("grade", selectedClass.getGrade().toString());
////                gradeList.add(gradeInfo);
////            }
////
////            return Response.Common.ok(gradeList);
////        } catch (NumberFormatException e) {
////            log.warn("Failed to parse card number", e);
////            return Response.Common.badRequest();
////        }
//    }

    @RouteMapping(uri = "TeachingEvaluaiotn/addEvaluation", role = "student")
    public Response addEvaluaiton(Request request, org.hibernate.Session database) {
        TeachingEvaluation newTeachingEvaluation = IEntity.fromJson(request.getParams().get("evaluation"), TeachingEvaluation.class);
        if (newTeachingEvaluation == null) {
            return Response.Common.badRequest();
        }
        newTeachingEvaluation.setUuid(UUID.randomUUID());
        Transaction tx = database.beginTransaction();
        database.persist(newTeachingEvaluation);
        tx.commit();
        return Response.Common.ok();
    }
}
