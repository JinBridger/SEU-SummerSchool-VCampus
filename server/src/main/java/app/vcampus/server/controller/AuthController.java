package app.vcampus.server.controller;

import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Password;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Session;
import app.vcampus.server.utility.router.RouteMapping;

import java.util.Map;

public class AuthController {
    /**
     * for user to log in by cardNumber and password
     *
     * @param request  from client with role and uri
     * @param database database
     * @return return response which contains the cardNumber and roles
     */
    @RouteMapping(uri = "auth/login")
    public Response login(Request request, org.hibernate.Session database) {
        try {
            String cardNum = request.getParams().get("cardNum");
            String password = request.getParams().get("password");

            if (cardNum == null || password == null) {
                return Response.Common.badRequest();
            }

            User user = database.get(User.class, Integer.parseInt(cardNum));
            if (user == null || !Password.verify(password, user.getPassword())) {
                return Response.Common.error("Incorrect card number or password");
            }
            user.setPassword(null);

            Response response = Response.Common.ok();
            Session session = new Session();
            session.setCardNum(user.getCardNum());
            session.setRoles(user.getRoles());
            response.setSession(session);

            response.setData(Map.of("user", user.toJson()));

            return response;
        } catch (Exception e) {
            return Response.Common.error(e.getMessage());
        }
    }

    /**
     * for user to logout
     *
     * @param request  from client with role and uri
     * @param database database
     * @return response with OK or error
     */
    @RouteMapping(uri = "auth/logout")
    public Response logout(Request request, org.hibernate.Session database) {
        Response response = Response.Common.ok();
        Session session = new Session();
        response.setSession(session);
        return response;
    }
}

