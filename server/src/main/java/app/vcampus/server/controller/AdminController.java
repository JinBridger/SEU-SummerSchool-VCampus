package app.vcampus.server.controller;

import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.Student;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Password;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class AdminController {
    /**
     * Solve client to add a new user for admin
     *     At the same time, If the user has a role of “student”,
     *     it creates a new Student object using the Student.getStudent() method and persists it to the database as well.
     * @param request  from client with role and uri
     * @param database database
     * @return a response which is ok or error
     */
    @RouteMapping(uri = "admin/user/add", role = "admin")
    public Response addUser(Request request, org.hibernate.Session database) {
        try {
            User user = IEntity.fromJson(request.getParams().get("user"), User.class);
            user.setPassword(Password.hash(user.password));

            Transaction tx = database.beginTransaction();
            database.persist(user);

            if (Arrays.stream(user.getRoles()).toList().contains("student")) {
                Student student = Student.getStudent(user);
                database.persist(student);
            }
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error(e.getMessage());
        }
    }


    /**
     * search the users in the database for admin
     *     two parameters: a Request object and a Session object.
     *     If the keyword is null or blank, it loads all user data using the loadAllData()
     *     Otherwise, it performs a like query on the User class using the likeQuery() s with the specified search fields and keyword.
     * @param request  from client with role and uri
     * @param database database
     * @return The method then returns an “OK” response with a list of JSON strings representing the matching users.
     */
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

    /**
     *     modify the user's roles for admin.
     *     search by the user's cardNumber
     *     If the user is not found,it returns an error response with a message “User not found”.
     *     Otherwise, it updates the user’s password and roles based on the values of the password and roleStr parameters.
     *     If password is not null or blank, it hashes the password using the Password.hash() method and sets it as the new password for the user.
     *     If roleStr is not null or blank, it splits it into an array of roles using a comma as a delimiter and sets it as the new roles for the user.
     *     If roleStr is null or blank, it sets an empty array as the new roles for the user.
     * @param request  from client with role and uri
     * @param database database
     * @return a response which is ok or error
     */
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

