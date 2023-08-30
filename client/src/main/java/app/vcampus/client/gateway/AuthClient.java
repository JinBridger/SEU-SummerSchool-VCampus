package app.vcampus.client.gateway;

import app.vcampus.server.entity.User;
import app.vcampus.server.enums.Gender;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.client.utility.Request;
import app.vcampus.client.utility.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.gson.internal.$Gson$Types.arrayOf;

public class AuthClient {
    public static User login(NettyHandler handler, String username, String password) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("auth/login");
        request.setParams(Map.of(
                "cardNum", username,
                "password", password
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
            Map<String, String> data = (Map<String, String>) response.get().getData();
            return User.fromMap(data);
        } else {
            return null;
        }
    }
}