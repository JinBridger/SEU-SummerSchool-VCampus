package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.Student;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class StudentStatusClient {
    /*
    Used to search Student's information
    @@param keyword -- search keyword
    if response is success , return List<Student>
    else throw an exception
     */
    public static List<Student> searchInfo(NettyHandler handler,
                                           String keyword) {
        Request request = new Request();
        request.setUri("student/filter");
        request.setParams(Map.of(
                "keyword", keyword
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                List<String> raw_data = ((Map<String, List<String>>) response.getData()).get("students");
                return raw_data.stream().map(json -> IEntity.fromJson(json, Student.class)).toList();
            } else {
                throw new RuntimeException("Failed to search student info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to search student info", e);
            return null;
        }
    }

    /*
    Used to update the information of an existing student
    @param student -- the new student's information to update
    if response is success , return true
    else throw an exception
    */
    public static boolean updateInfo(NettyHandler handler, Student student) {
        Request request = new Request();
        request.setUri("student/updateInfo");
        request.setParams(Map.of(
                "student", student.toJson()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                return true;
            } else {
                throw new RuntimeException("Failed to update student info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to update student info", e);
            return false;
        }
    }

    /*
    Used to get the information of the student who is now logged in
    if response is success , return the Student , it will contain the student's information
    else throw an exception
    */
    public static Student getSelf(NettyHandler handler) {
        Request request = new Request();
        request.setUri("student/getSelf");

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                String data = ((Map<String, String>) response.getData()).get("student");
                return IEntity.fromJson(data, Student.class);
            } else {
                throw new RuntimeException("Failed to get self");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get self", e);
            return null;
        }
    }

}

