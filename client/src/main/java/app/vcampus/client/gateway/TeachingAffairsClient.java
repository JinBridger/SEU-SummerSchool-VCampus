package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.TeachingClass;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class TeachingAffairsClient {
    /**
     * Used to get selected class.
     * @param handler
     * @return if the response is success,List<TeachingClass>,else throw exception.
     * @throws Exception If an error occurs.
     */
    public static List<TeachingClass> getSelectedClasses(NettyHandler handler) {
        Request request = new Request();
        request.setUri("teaching/student/getMyClasses");

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                List<String> raw_data = ((Map<String, List<String>>) response.getData()).get("classes");
                return raw_data.stream().map((String s) -> IEntity.fromJson(s, TeachingClass.class)).toList();
            } else {
                throw new Exception(response.getMessage());
            }
        } catch (Exception e) {
            log.warn("Fail to get selected classes", e);
            return null;
        }
    }

    /**
     Used to get selected classes information.
     @param evaluationResult
     @return if response is success , return List<TeachingClass>
     @throws Exception If an error occurs.
     */
    public static Boolean sendEvaluationResult(NettyHandler handler, Pair<UUID, Pair<List<Integer>, String>> evaluationResult) {
        Request request = new Request();
        request.setUri("teaching/student/submitEvaluation");
        request.setParams(Map.of(
                "evaluation", BaseClient.toJson(evaluationResult)
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            return response.getStatus().equals("success");
        } catch (Exception e) {
            log.warn("Fail to submit evaluation", e);
            return false;
        }
    }


    /**
     This method is used to get selected classes information table.
     @return List<Course>
     @throws Exception If an error occurs.
     */
    public static List<Course> getSelectableCourses(NettyHandler handler) {
        Request request = new Request();
        request.setUri("teaching/student/getSelectableCourses");

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                List<String> raw_data = ((Map<String, List<String>>) response.getData()).get("courses");
                return raw_data.stream().map((String s) -> IEntity.fromJson(s, Course.class)).toList();
            } else {
                throw new Exception(response.getMessage());
            }
        } catch (Exception e) {
            log.warn("Fail to get selectable courses", e);
            return null;
        }
    }

    /**
     Used to choose class.
     @param classUuid
     @return if response is success , return success, else false.
     @throws Exception If an error occurs.
     */
    public static Boolean chooseClass(NettyHandler handler, UUID classUuid) {
        Request request = new Request();
        request.setUri("teaching/student/chooseClass");
        request.setParams(Map.of(
                "classUuid", classUuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            return response.getStatus().equals("success");
        } catch (Exception e) {
            log.warn("Fail to choose class", e);
            return false;
        }
    }

    /**
     Used to drop class.
     @param  classUuid
     @return if response is success , return success, else false.

     @throws Exception If an error occurs.
     */
    public static Boolean dropClass(NettyHandler handler, UUID classUuid) {
        Request request = new Request();
        request.setUri("teaching/student/dropClass");
        request.setParams(Map.of(
                "classUuid", classUuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            return response.getStatus().equals("success");
        } catch (Exception e) {
            log.warn("Fail to drop class", e);
            return false;
        }
    }


    /**
     * Used to get my teaching classes for teachers.
     * @param handler
     * @return List<TeachingClass>
     * @throws Exception If an error occurs.
     */
    public static List<TeachingClass> getMyTeachingClasses(NettyHandler handler) {
        Request request = new Request();
        request.setUri("teaching/teacher/getMyClasses");

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                List<String> raw_data = ((Map<String, List<String>>) response.getData()).get("classes");
                return raw_data.stream().map((String s) -> IEntity.fromJson(s, TeachingClass.class)).toList();
            } else {
                throw new Exception(response.getMessage());
            }
        } catch (Exception e) {
            log.warn("Fail to get teaching classes", e);
            return null;
        }
    }

    /**
     * Used to drop class.
     * @param handler
     * @param classUuid
     * @return String
     * @throws Exception If an error occurs.
     */
    public static String exportStudentList(NettyHandler handler, UUID classUuid) {
        Request request = new Request();
        request.setUri("teaching/teacher/exportStudentList");
        request.setParams(Map.of(
                "classUuid", classUuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                return ((Map<String, String>) response.getData()).get("file");
            } else {
                throw new Exception(response.getMessage());
            }
        } catch (Exception e) {
            log.warn("Fail to export student list", e);
            return null;
        }
    }

    /**
     * Used to export grade template.
     * @param handler
     * @param classUuid
     * @return String
     * @throws Exception If an error occurs.
     */
    public static String exportGradeTemplate(NettyHandler handler, UUID classUuid) {
        Request request = new Request();
        request.setUri("teaching/teacher/exportGradeTemplate");
        request.setParams(Map.of(
                "classUuid", classUuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                return ((Map<String, String>) response.getData()).get("file");
            } else {
                throw new Exception(response.getMessage());
            }
        } catch (Exception e) {
            log.warn("Fail to export grade template", e);
            return null;
        }
    }

    /**
     * Used to export grade template.
     * @param handler
     * @param classUuid
     * @param file The file name.
     * @return true if success
     * @throws Exception If an error occurs.
     */
    public static Boolean importGrade(NettyHandler handler, UUID classUuid, String file) {
        Request request = new Request();
        request.setUri("teaching/teacher/importGrade");
        request.setParams(Map.of(
                "classUuid", classUuid.toString(),
                "file", file
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            return response.getStatus().equals("success");
        } catch (Exception e) {
            log.warn("Fail to import grade", e);
            return false;
        }
    }

//    public static Course addCourse(NettyHandler handler, String uuid, String courseName, String courseId, String school, String credit) {
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicReference<Response> response = new AtomicReference();
//        Request request = new Request();
//        request.setUri("course/addCourse");
//        request.setParams(Map.of("uuid", uuid, "courseName", courseName, "courseId", courseId, "school", school, "credit", credit));
//        handler.sendRequest(request, (res) -> {
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//        try {
//            latch.await();
//        } catch (InterruptedException var16) {
//            var16.printStackTrace();
//            return null;
//        }
//
//        if ((response.get()).getStatus().equals("success")) {
//            Map<String, String> data = (Map) ((Map) ((Response) response.get()).getData()).get("course");
//            Course course = Course.fromMap(data);
//            return course;
//        } else {
//            return null;
//        }
//    }
//
//
//    public static Map<String,List<Course>> searchCourse(NettyHandler handler,String keyword){
//        Request request=new Request();
//        request.setUri("course/searchBook");
//        request.setParams(Map.of(
//                "keyword",keyword
//        ));
//
//        try {
//            Response response = BaseClient.sendRequest(handler, request);
//            if (response.getStatus().equals("success")) {
//                Map<String, List<String>> raw_data = (Map<String, List<String>>) response.getData();
//                Map<String, List<Course>> data = new HashMap<>();
//                raw_data.forEach((key, value) -> data.put(key, value.stream().map(json -> IEntity.fromJson(json, Course.class)).toList()));
//                return data;
//            } else {
//                throw new RuntimeException("Failed to get course info");
//            }
//        }catch (InterruptedException e){
//            log.warn("Fail to get course info",e);
//            return null;
//        }
//    }

    //This method is used for student to select teaching class.
//    public static SelectedClass selectClass(NettyHandler handler, String uuid, String classUuid, String cardNumber, String selectTime, String grade) {
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicReference<Response> response = new AtomicReference();
//        Request request = new Request();
//        request.setUri("selectedClass/selectClass");
//        request.setParams(Map.of("uuid", uuid, "classUuid", classUuid, "cardNumber", cardNumber, "selectTime", selectTime, "grade", grade));
//        handler.sendRequest(request, (res) -> {
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try {
//            latch.await();
//        } catch (InterruptedException var16) {
//            var16.printStackTrace();
//            return null;
//        }
//        if ((response.get()).getStatus().equals("success")) {
//            Map<String, String> data = (Map) ((Map) ((Response) response.get()).getData()).get("selectedClass");
//            SelectedClass selectedClass = SelectedClass.fromMap(data);
//            return selectedClass;
//        } else {
//            return null;
//        }
//    }

    //Used to search course information.
//    public static Course searchInfo(NettyHandler handler, String courseName) {
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicReference<Response> response = new AtomicReference();
//        Request request = new Request();
//        request.setUri("selectedClass/searchInfo");
//        request.setParams(Map.of("courseName", courseName));
//        handler.sendRequest(request, (res) ->
//        {
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try {
//            latch.await();
//        } catch (InterruptedException var6) {
//            var6.printStackTrace();
//            return null;
//        }
//
//        if ((response.get()).getStatus().equals("success")) {
//            Map<String, String> data = (Map) (response.get()).getData();
//            return Course.fromMap(data);
//        } else {
//            return null;
//        }
//    }
//
//    public static boolean recordGrade(NettyHandler handler, Integer grade) {
//        Request request = new Request();
//        request.setUri("selectedClass/grade");
//        request.setParams(Map.of(
//                "grade", grade.toString()
//        ));
//        try {
//            Response response = BaseClient.sendRequest(handler, request);
//            return response.getStatus().equals("success");
//        } catch (InterruptedException e) {
//            log.warn("Fail to  record grade", e);
//            return false;
//        }
//    }
//
//    public static boolean updateCourse(NettyHandler handler, LibraryBook book) {
//        Request request = new Request();
//        request.setUri("affairs_staff/updateCourse");
//        request.setParams(Map.of(
//                "course", book.toJson()
//        ));
//
//        try {
//            Response response = BaseClient.sendRequest(handler, request);
//            return response.getStatus().equals("success");
//        } catch (InterruptedException e) {
//            log.warn("Fail to update course", e);
//            return false;
//        }
//    }
//
//    public static boolean deleteCourse(NettyHandler handler, UUID uuid)
//    {
//        Request request=new Request();
//        request.setUri("affairs_staff/deleteCourse");
//        request.setParams(Map.of(
//                "uuid",uuid.toString()
//        ));
//
//        try{
//            Response response=BaseClient.sendRequest(handler,request);
//            return response.getStatus().equals("success");
//        }catch(InterruptedException e) {
//            log.warn("Fail to delete course",e);
//            return false;
//    }
//    }
}
