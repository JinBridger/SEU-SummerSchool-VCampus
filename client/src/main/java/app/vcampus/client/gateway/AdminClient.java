package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class AdminClient {
    public static Boolean addUser(NettyHandler handler, User user) {
        Request request = new Request();
        request.setUri("admin/user/add");
        request.setParams(Map.of(
                "user", user.toJson()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to add user", e);
            return false;
        }
    }

    public static List<User> searchUser(NettyHandler handler, String keyword) {
        Request request = new Request();
        request.setUri("admin/user/search");
        request.setParams(Map.of(
                "keyword", keyword
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                List<String> raw_data = (List<String>) response.getData();
                return raw_data.stream().map(json -> IEntity.fromJson(json, User.class)).toList();
            } else {
                throw new RuntimeException("Failed to search user");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to search user", e);
            return null;
        }
    }

    public static Boolean modifyUser(NettyHandler handler, Integer cardNum, String password, List<String> roles) {
        Request request = new Request();
        request.setUri("admin/user/modify");
        request.setParams(Map.of(
                "cardNum", cardNum.toString(),
                "password", password,
                "roles", String.join(",", roles)
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to modify user", e);
            return false;
        }
    }
}
