package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

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
                String data = ((Map<String, String>) response.getData()).get("user");
                return IEntity.fromJson(data, User.class);
            } else {
                throw new RuntimeException("Failed to login");
            }
        } catch (Exception e) {
            log.warn("Fail to login", e);
            return null;
        }
    }
}
