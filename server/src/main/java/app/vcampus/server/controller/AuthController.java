package app.vcampus.server.controller;

import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Password;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Session;
import app.vcampus.server.utility.router.RouteMapping;

public class AuthController {
    @RouteMapping(uri = "auth/login")
    public Response login(Request request, org.hibernate.Session database) {
        String cardNum = request.getParams().get("cardNum");
        String password = request.getParams().get("password");

        if (cardNum == null || password == null) {
            return Response.Common.badRequest();
        }

        User user = database.get(User.class, Integer.parseInt(cardNum));
        if (user == null || !Password.verify(password, user.getPassword())) {
            return Response.Common.error("Incorrect card number or password");
        }

        Response response = Response.Common.ok();
        Session session = new Session();
        session.setCardNum(user.getCardNum());
        session.setRoles(user.getRoles());
        response.setSession(session);
        return response;
    }

    @RouteMapping(uri = "auth/test", role = "admin")
    public Response test(Request request, org.hibernate.Session database) {
        return Response.Common.ok();
    }
}
