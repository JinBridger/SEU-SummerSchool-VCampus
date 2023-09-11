package app.vcampus.server.controller;

import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Password;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.List;

@Slf4j
public class AdminController {
    @RouteMapping(uri = "admin/user/add", role = "admin")
    public Response addUser(Request request, org.hibernate.Session database) {
        // TODO: implement
        return Response.Common.ok();
    }

    @RouteMapping(uri = "admin/user/search", role = "admin")
    public Response searchUser(Request request, org.hibernate.Session database) {
        try {
            String keyword = request.getParams().get("keyword");
            List<User> users;
            if (keyword == null || keyword.isBlank()) {
                users = Database.loadAllData(User.class, database);
            } else {
                users = Database.likeQuery(User.class, new String[]{"cardNum", "name", "phone", "email"}, keyword, database);
            }

            return Response.Common.ok(users.stream().peek(user -> user.setPassword(null)).map(User::toJson).toList());
        } catch (Exception e) {
            return Response.Common.error(e.getMessage());
        }
    }

    @RouteMapping(uri = "admin/user/modify", role = "admin")
    public Response modifyUser(Request request, org.hibernate.Session database) {
        try {
            String password = request.getParams().get("password");
            String roleStr = request.getParams().get("roles");
            int cardNum = Integer.parseInt(request.getParams().get("cardNum"));

            User user = database.get(User.class, cardNum);
            if (user == null) {
                return Response.Common.error("User not found");
            }

            if (password != null && !password.isBlank()) {
                user.setPassword(Password.hash(password));
            }

            if (roleStr != null && !roleStr.isBlank()) {
                user.setRoles(roleStr.split(","));
            } else {
                user.setRoles(new String[]{});
            }

            Transaction tx = database.beginTransaction();
            database.merge(user);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error(e.getMessage());
        }
    }
}