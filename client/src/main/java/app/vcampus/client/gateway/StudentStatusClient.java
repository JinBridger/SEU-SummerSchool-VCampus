package app.vcampus.client.gateway;

import app.vcampus.server.entity.Student;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import app.vcampus.server.utility.DateUtility;

public class StudentStatusClient {

//    public static boolean deleteInfo(NettyHandler handler, String cardNumber){
//        CountDownLatch latch = new CountDownLatch(1);
//        AtomicReference<Response> response = new AtomicReference<>();
//        Request request = new Request();
//        request.setUri("student/deleteInfo");
//        request.setParams(Map.of(
//                "cardNumber", cardNumber
//        ));
//
//        handler.sendRequest(request, res -> {
//            response.set(res);
//            System.out.println(res);
//            latch.countDown();
//        });
//
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        if(response.get().getStatus().equals("success")){
//            System.out.println("delete complete ");
//            return true;
//        } else{
//            return false;
//        }
//    }
//
    public static <givenName, birthDate, gender> Student addInfo(NettyHandler handler,
                                                                 String cardNumber,
                                                                 String studentNumber,
                                                                 String major,
                                                                 String school,
                                                                 String birthPlace,
                                                                 String status,
                                                                 String politicalStatus,
                                                                 String givenName,
                                                                 String familyName,
                                                                 String birthDate) {
        CountDownLatch latch = new CountDownLatch(1);
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
                "politicalStatus", politicalStatus,
                "givenName",givenName,
                "familyName",familyName,
                "birthDate",birthDate
//                "gender",gender
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

        if (response.get().getStatus().equals("success")) {
            Map<String, String> data = (Map<String, String>) ((Map<String, Object>) response.get().getData()).get("student");
            Student student = Student.fromMap(data);
            return student;
        } else {
            return null;
        }
    }

    public static Student searchInfo(NettyHandler handler,
                                     String cardNumber){
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

}
