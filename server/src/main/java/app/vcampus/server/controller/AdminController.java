package app.vcampus.server.controller;

import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Password;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.router.RouteMapping;

public class AdminController {
    @RouteMapping(uri = "admin/user/add", role = "admin")
    public void addUser(Request request, org.hibernate.Session database) {
        // TODO: implement
    }
}
