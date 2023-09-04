package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class AuthClient {
    public static User login(NettyHandler handler, String username, String password) {
        Request request = new Request();
        request.setUri("auth/login");
        request.setParams(Map.of(
                "cardNum", username,
                "password", password
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                Map<String, String> data = (Map<String, String>) response.getData();
                return User.fromMap(data);
            } else {
                //throw new RuntimeException("Failed to login");
                return null;
            }
        } catch (InterruptedException e) {
            log.warn("Fail to login", e);
            return null;
        }
    }
}
