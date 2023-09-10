package app.vcampus.server.controller;

import app.vcampus.server.entity.*;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import com.alibaba.excel.EasyExcel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
public class TeachingAffairsController {
    @RouteMapping(uri = "teaching/student/getMyClasses", role = "student")
    public Response getSelectedClasses(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();

        List<SelectRecord> selectRecords = Database.getWhereString(SelectRecord.class, "cardNumber", Integer.toString(cardNumber), database);
        List<TeachingClass> teachingClasses = selectRecords.stream().map((SelectRecord sc) -> {
            Grades grades = sc.getGrade();
            if (grades != null) {
                List<SelectRecord> classmates = Database.getWhereUuid(SelectRecord.class, "classUuid", sc.getClassUuid(), database);
                List<Grades> gradesList = classmates.stream().map(SelectRecord::getGrade).toList();
                grades.classAvg = gradesList.stream().mapToInt(Grades::getTotal).average().orElse(0);
                grades.classMax = gradesList.stream().mapToInt(Grades::getTotal).max().orElse(0);
                grades.classMin = gradesList.stream().mapToInt(Grades::getTotal).min().orElse(0);
                sc.setGrade(grades);
            }

            TeachingClass teachingClass = database.get(TeachingClass.class, sc.getClassUuid());
            teachingClass.setSelectRecord(sc);
            return teachingClass;
        }).toList();

        List<TeachingEvaluation> evaluations = Database.getWhereString(TeachingEvaluation.class, "studentId", Integer.toString(cardNumber), database);

        teachingClasses = teachingClasses.stream().peek((TeachingClass tc) -> {
            User teacher = database.get(User.class, tc.getTeacherId());
            tc.setTeacherName(teacher.getName());

            Course course = database.get(Course.class, tc.getCourseUuid());
            tc.setCourse(course);

            tc.setIsEvaluated(evaluations.stream().anyMatch((TeachingEvaluation te) -> te.classUuid.equals(tc.getUuid())));
        }).toList();

        return Response.Common.ok(Map.of("classes", teachingClasses.stream().map(TeachingClass::toJson).toList()));
    }

    @RouteMapping(uri = "teaching/student/submitEvaluation", role = "student")
    public Response submitEvaluation(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();
        Type type = new TypeToken<Pair<UUID, Pair<List<Integer>, String>>>(){}.getType();
        Pair<UUID, Pair<List<Integer>, String>> evaluationResult = new Gson().fromJson(request.getParams().get("evaluation"), type);

        try {
            Transaction tx = database.beginTransaction();
            TeachingEvaluation newTeachingEvaluation = new TeachingEvaluation();
            newTeachingEvaluation.setUuid(UUID.randomUUID());
            newTeachingEvaluation.setClassUuid(evaluationResult.getFirst());
            newTeachingEvaluation.setStudentId(cardNumber);
            newTeachingEvaluation.setResult(evaluationResult.getSecond().getFirst());
            newTeachingEvaluation.setComment(evaluationResult.getSecond().getSecond());
            database.persist(newTeachingEvaluation);
            tx.commit();
            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to add evaluation", e);
            return Response.Common.badRequest();
        }
    }

    @RouteMapping(uri = "teaching/student/getSelectableCourses", role = "student")
    public Response getSelectableCourses(Request request, org.hibernate.Session database) {
        List<Course> courses = Database.loadAllData(Course.class, database);
        courses = courses.stream().peek((Course course) -> {
            List<TeachingClass> teachingClasses = Database.getWhereUuid(TeachingClass.class, "courseUuid", course.getUuid(), database);
            teachingClasses = teachingClasses.stream().peek((TeachingClass tc) -> {
                User teacher = database.get(User.class, tc.getTeacherId());
                tc.setTeacherName(teacher.getName());

                tc.setSelectedCount(Database.getWhereUuid(SelectRecord.class, "classUuid", tc.getUuid(), database).size());
            }).toList();
            course.setTeachingClasses(teachingClasses);
        }).toList();

        return Response.Common.ok(Map.of("courses", courses.stream().map(Course::toJson).toList()));
    }

    @RouteMapping(uri = "teaching/student/evaluate", role = "student")
    public Response evaluateClass(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();
        TeachingEvaluation newTeachingEvaluation = IEntity.fromJson(request.getParams().get("evaluation"), TeachingEvaluation.class);

        newTeachingEvaluation.studentId = cardNumber;

        try {
            Transaction tx = database.beginTransaction();
            database.persist(newTeachingEvaluation);
            tx.commit();
            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to add evaluation", e);
            return Response.Common.badRequest();
        }
    }

    @RouteMapping(uri = "teaching/student/chooseClass", role = "student")
    public Response selectClass(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();
        UUID classUuid = UUID.fromString(request.getParams().get("classUuid"));

        SelectRecord selectRecord = new SelectRecord();
        selectRecord.setUuid(UUID.randomUUID());
        selectRecord.setCardNumber(cardNumber);
        selectRecord.setClassUuid(classUuid);
        selectRecord.setSelectTime(new Date());

        try {
            Transaction tx = database.beginTransaction();
            database.persist(selectRecord);
            tx.commit();
            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to select class", e);
            return Response.Common.badRequest();
        }
    }

    @RouteMapping(uri = "teaching/student/dropClass", role = "student")
    public Response dropClass(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();
        UUID classUuid = UUID.fromString(request.getParams().get("classUuid"));

        SelectRecord selectRecord = Database.getWhereUuid(SelectRecord.class, "classUuid", classUuid, database).stream()
                .filter((SelectRecord sr) -> sr.getCardNumber().equals(cardNumber))
                .findFirst().orElse(null);

        if (selectRecord == null) {
            return Response.Common.error("No such class");
        }

        if (selectRecord.getGrade() != null) {
            return Response.Common.error("Cannot drop class after grades are recorded");
        }

        try {
            Transaction tx = database.beginTransaction();
            database.remove(selectRecord);
            tx.commit();
            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to drop class", e);
            return Response.Common.badRequest();
        }
    }

    @RouteMapping(uri = "teaching/teacher/getMyClasses", role = "teacher")
    public Response getMyClasses(Request request, org.hibernate.Session database) {
        int cardNumber = request.getSession().getCardNum();
        List<TeachingClass> teachingClasses = Database.getWhereString(TeachingClass.class, "teacherId", Integer.toString(cardNumber), database);
        teachingClasses = teachingClasses.stream().peek((TeachingClass tc) -> {
            Course course = database.get(Course.class, tc.getCourseUuid());
            tc.setCourse(course);

            List<TeachingEvaluation> evaluations = Database.getWhereUuid(TeachingEvaluation.class, "classUuid", tc.getUuid(), database);
            List<List<Integer>> evaluationRatings = new ArrayList<>();
            List<String> evaluationComments = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                evaluationRatings.add(new ArrayList<>());
                for (int j = 0; j < 10; j++) {
                    evaluationRatings.get(i).add(0);
                }
            }
            evaluations.forEach((TeachingEvaluation te) -> {
                final int[] index = {0};
                te.result.forEach((Integer score) -> {
                    evaluationRatings.get(index[0]).set(score - 1, evaluationRatings.get(index[0]).get(score - 1) + 1);
                    index[0]++;
                });

                if (!te.comment.isBlank()) evaluationComments.add(te.comment);
            });
            tc.setEvaluationResult(new Pair<>(evaluationRatings, evaluationComments));
        }).toList();

        return Response.Common.ok(Map.of("classes", teachingClasses.stream().map(TeachingClass::toJson).toList()));
    }

    @RouteMapping(uri = "teaching/teacher/exportStudentList", role = "teacher")
    public Response exportStudentList(Request request, org.hibernate.Session database) {
        try {
            UUID classUuid = UUID.fromString(request.getParams().get("classUuid"));
            List<SelectRecord> selectRecords = Database.getWhereUuid(SelectRecord.class, "classUuid", classUuid, database);

            Path tmpFile = Files.createTempFile("studentList", ".xlsx");
            tmpFile.toFile().deleteOnExit();
            EasyExcel.write(tmpFile.toFile(), ExcelStudentList.class)
                    .sheet()
                    .doWrite(selectRecords.stream().map((SelectRecord sr) -> {
                        Student student = database.get(Student.class, sr.getCardNumber());
                        return new ExcelStudentList(student.getStudentNumber(), student.getFamilyName() + student.getGivenName());
                    }).toList());

            return Response.Common.ok(Map.of("file", Base64.getEncoder().encodeToString(Files.readAllBytes(tmpFile))));
        } catch (Exception e) {
            log.warn("Failed to create Excel", e);
            return Response.Common.error("Failed to export");
        }
    }

    @RouteMapping(uri = "teaching/teacher/exportGradeTemplate", role = "teacher")
    public Response getGradeTemplate(Request request, org.hibernate.Session database) {
        try {
            UUID classUuid = UUID.fromString(request.getParams().get("classUuid"));
            List<SelectRecord> selectRecords = Database.getWhereUuid(SelectRecord.class, "classUuid", classUuid, database);

            Path tmpFile = Files.createTempFile("gradeTemplate", ".xlsx");
            tmpFile.toFile().deleteOnExit();
            EasyExcel.write(tmpFile.toFile(), ExcelStudentGrade.class)
                    .sheet()
                    .doWrite(selectRecords.stream().map((SelectRecord sr) -> {
                        Student student = database.get(Student.class, sr.getCardNumber());
                        return new ExcelStudentGrade(student.getStudentNumber(), student.getFamilyName() + student.getGivenName());
                    }).toList());

            return Response.Common.ok(Map.of("file", Base64.getEncoder().encodeToString(Files.readAllBytes(tmpFile))));
        } catch (Exception e) {
            log.warn("Failed to create Excel", e);
            return Response.Common.error("Failed to export");
        }
    }

    @RouteMapping(uri = "teaching/teacher/importGrade", role = "teacher")
    public Response importGrade(Request request, org.hibernate.Session database) {
        try {
            UUID classUuid = UUID.fromString(request.getParams().get("classUuid"));
            String file = request.getParams().get("file");
            byte[] fileBytes = Base64.getDecoder().decode(file);
            Path tmpFile = Files.createTempFile("gradeTemplate", ".xlsx");
            tmpFile.toFile().deleteOnExit();
            Files.write(tmpFile, fileBytes);

            List<ExcelStudentGrade> studentGrades = EasyExcel.read(tmpFile.toFile()).head(ExcelStudentGrade.class).sheet().doReadSync();

            Transaction tx = database.beginTransaction();
            for (ExcelStudentGrade studentGrade : studentGrades) {
                Student student = Database.getWhereString(Student.class, "studentNumber", studentGrade.getStudentNumber(), database).stream().findFirst().orElse(null);
                if (student == null) {
                    return Response.Common.error("No such student");
                }
                SelectRecord selectRecord = Database.getWhereUuid(SelectRecord.class, "classUuid", classUuid, database).stream()
                        .filter((SelectRecord sr) -> sr.getCardNumber().equals(student.getCardNumber()))
                        .findFirst().orElse(null);

                if (selectRecord == null) {
                    return Response.Common.error("No such student");
                }

                Grades grades = new Grades();
                grades.setGeneral(studentGrade.getGeneral());
                grades.setMidterm(studentGrade.getMidterm());
                grades.setFinalExam(studentGrade.getFinalExam());
                grades.setTotal(studentGrade.getTotal());

                selectRecord.setGrade(grades);
                database.merge(selectRecord);
            }
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            log.warn("Failed to create Excel", e);
            return Response.Common.error("Failed to import");
        }
    }

//    @RouteMapping(uri = "course/addCourse", role = "affairs_staff")
//    public Response addCourse(Request request, org.hibernate.Session database) {
//        Course newCourse = Course.fromMap(request.getParams());
//        if (newCourse == null) {
//            return Response.Common.badRequest();
//        }
//
//        newCourse.setUuid(UUID.randomUUID());
//        Transaction tx = database.beginTransaction();
//        Database.updateWhere(Course.class, "courseId", newCourse.getCourseId(), List.of(
//                new Pair<>("courseName", newCourse.getCourseName()),
////                new Pair<>("school", Integer.toString(newCourse.getSchool())),
//                new Pair<>("credit", Float.toString(newCourse.getCredit()))
//        ), database);
//        database.persist(newCourse);
//        tx.commit();
//
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri = "teaching/deleteCourse", role = "affairs_staff")
//    public Response deleteCourse(Request request, org.hibernate.Session database) {
//        String id = request.getParams().get("uuid");
//
//        if (id == null) return Response.Common.error("Course UUID cannot be empty");
//
//        UUID uuid = UUID.fromString(id);
//        Course toDelete = database.get(Course.class, uuid);
//        if (toDelete == null) return Response.Common.error("No such course");
//
//        Transaction tx = database.beginTransaction();
//        database.remove(toDelete);
//        tx.commit();
//
//        return Response.Common.ok();
//    }

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
//    @RouteMapping(uri = "class/searchClass", role = "teacher")
//    public Response searchClass(Request request, org.hibernate.Session database) {
//        String cardNumber = request.getParams().get("cardNumber");
//
//        if (cardNumber == null) {
//            return Response.Common.error("card number cannot be empty");
//        }
//
//        TeachingClass teachingClass = database.get(TeachingClass.class, Integer.parseInt(cardNumber));
//        if (teachingClass == null) {
//            return Response.Common.error("no such card number");
//        }
//
//        System.out.println(teachingClass);
//
//        return Response.Common.ok(teachingClass.toMap());
//    }

//@RouteMapping(uri="teaching/updateCourse",role="affairs_staff")
//public Response updateCourse(Request request, org.hibernate.Session database)
//{
//    Course newCourse=IEntity.fromJson(request.getParams().get("course"), Course.class);
//    Course toUpdate=database.get(Course.class,newCourse.getUuid());
//    if(toUpdate==null)
//    {
//        return Response.Common.badRequest();
//    }
//
//    Transaction tx=database.beginTransaction();
//    toUpdate.setCourseId(newCourse.getCourseId());
//    toUpdate.setCourseName(newCourse.getCourseName());
//    toUpdate.setCredit(newCourse.getCredit());
//    toUpdate.setSchool(newCourse.getSchool());
//    database.persist(toUpdate);
//    tx.commit();
//
//    return Response.Common.ok();
//}


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

//    @RouteMapping(uri = "teaching/recordGrade", role = "affairs_staff")
//    public Response fakeRecordGrade(Request request, org.hibernate.Session database) {
//        String studentUuid = request.getParams().get("studentUuid");
//        String classUuid = request.getParams().get("classUuid");
//        String general = request.getParams().get("general");
//        String midterm = request.getParams().get("midterm");
//        String finalExam = request.getParams().get("finalExam");
//
//        if (studentUuid == null || classUuid == null ) {
//            return Response.Common.badRequest();
//        }
//
//        try {
//            UUID studentId = UUID.fromString(studentUuid);
//            UUID courseId = UUID.fromString(classUuid);
//            int generalGrade = Integer.parseInt(general);
//            int midtermGrade = Integer.parseInt(midterm);
//            int finalExamGrade = Integer.parseInt(finalExam);
//
//            SelectRecord selectRecord = database.get(SelectRecord.class, studentId);
//
//            if (selectRecord == null) {
//                return Response.Common.error("SelectedClass not found for student");
//            }
//
//            Grades grades = new Grades();
//            grades.setGeneral(generalGrade);
//            grades.setMidterm(midtermGrade);
//            grades.setFinalExam(finalExamGrade);
//            grades.setTotal ((int) (0.1*generalGrade + 0.3*midtermGrade + 0.6*finalExamGrade));
//            //fake setTotal method
//
//            List<SelectRecord> classmates = Database.getWhereUuid(SelectRecord.class, "classUuid", courseId, database);
//            List<Integer> totalGrades = classmates.stream().map(sc -> sc.getGrade().getTotal()).toList();
//
//            grades.setClassMax(Collections.max(totalGrades));
//            grades.setClassMin(Collections.min(totalGrades));
//            grades.setClassAvg(totalGrades.stream().mapToInt(Integer::intValue).average().orElse(0));
//
//            selectRecord.setGrade(grades);
//            database.merge(selectRecord);
//
//            return Response.Common.ok();
//        } catch (Exception e) {
//            log.warn("Failed to record grades", e);
//            return Response.Common.error("Failed to record grades");
//        }
//    }

}
