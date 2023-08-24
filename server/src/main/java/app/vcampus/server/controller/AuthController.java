package app.vcampus.server.controller;

import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.Session;
import app.vcampus.server.utility.Utility;
import app.vcampus.server.utility.router.RouteMapping;

public class AuthController {
    @RouteMapping(uri = "auth/login")
    public Response login(Request request) {
        Response response = Utility.ok();
        Session session = new Session();
        session.setRoles(new String[]{"admin"});
        response.setSession(session);
        return response;
    }

    @RouteMapping(uri = "auth/test", role = "admin")
    public Response test(Request request) {
        return Utility.ok();
    }
}
