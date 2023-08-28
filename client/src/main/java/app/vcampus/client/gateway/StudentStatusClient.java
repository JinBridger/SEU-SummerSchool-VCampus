package app.vcampus.client.gateway;

import app.vcampus.server.entity.Student;
import app.vcampus.server.enums.PoliticalStatus;
import app.vcampus.server.enums.Status;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.client.utility.Request;
import app.vcampus.client.utility.Response;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
public class StudentStatusClient {

    public static boolean deleteInfo(NettyHandler handler, String cardNumber){
        CountDownLatch latch = new CountDownLatch(3);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("student/deleteInfo");
        request.setParams(Map.of(
                "cardNumber", cardNumber
        ));
        handler.sendRequest(request, res -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        if(response.get().getStatus().equals("success")){
            System.out.println("delete complete ");
            return true;
        } else{
            return false;
        }
    }

    public static Student addInfo(NettyHandler handler,
                                  String cardNumber,
                                  String studentNumber,
                                  String major,
                                  String school,
                                  String birthPlace,
                                  String status,
                                  String politicalStatus) {
        CountDownLatch latch = new CountDownLatch(2);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("student/addInfo");
        request.setParams(Map.of(
                "cardNumber", cardNumber,
                "studentNumber", studentNumber,
                "major", major,
                "school", school,
                "birthPlace", birthPlace,
                "status", status,
                "politicalStatus", politicalStatus
        ));
        handler.sendRequest(request, res -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        if (response.get().getStatus().equals("success")) {
            Student student = new Student();
            Map<String, String> data = (Map<String, String>) ((Map<String, Object>) response.get().getData()).get("student");
            student.setCardNumber(Integer.valueOf(data.get("cardNumber")));
            student.setStudentNumber(data.get("studentNumber"));
            student.setMajor(Integer.valueOf(data.get("major")));
            student.setSchool(Integer.valueOf(data.get("school")));
            student.setBirthPlace(data.get("birthPlace"));
            student.setStatus(Status.valueOf(data.get("status")));
            student.setPoliticalStatus(PoliticalStatus.valueOf(data.get("politicalStatus")));
            return student;
        } else {
            return null;
        }
    }
    public static Student searchInfo(NettyHandler handler,
                                     String cardNumber,
                                     String studentNumber,
                                     String major,
                                     String school,
                                     String birthPlace,
                                     String status,
                                     String politicalStatus){
            CountDownLatch latch = new CountDownLatch(1);
            AtomicReference<Response> response = new AtomicReference<>();
            Request request = new Request();
            request.setUri("student/searchInfo");
            request.setParams(Map.of(
                    "cardNumber", cardNumber
            ));
            handler.sendRequest(request, res -> {
                response.set(res);
                System.out.println(res);
                latch.countDown();
            });

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }

            if(response.get().getStatus().equals("success")){
                Map<String, String> data = (Map<String, String>) response.get().getData();
                return Student.fromMap(data);
            } else{
                return null;
            }
        }

    public static Student getSelf(NettyHandler handler){
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("student/getSelf");
        handler.sendRequest(request, res -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        if(response.get().getStatus().equals("success")){
            Map<String, String> data = (Map<String, String>) response.get().getData();
            return Student.fromMap(data);
        } else{
            return null;
        }
    }

}
