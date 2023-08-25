package app.vcampus.server.controller;

import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;

public class IndexController {
    @RouteMapping(uri = "heartbeat")
    public Response heartbeat(Request request, org.hibernate.Session database) {
        return Response.Common.ok();
    }
}
