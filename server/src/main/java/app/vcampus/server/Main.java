package app.vcampus.server;

import app.vcampus.server.controller.AuthController;
import app.vcampus.server.controller.IndexController;
import app.vcampus.server.controller.StudentStatusController;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.router.Router;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.addController(AuthController.class);
        router.addController(IndexController.class);
        router.addController(StudentStatusController.class);

        Session database = Database.init();

//        Transaction tx = database.beginTransaction();
//        User user = new User();
//        user.setCardNum(123456);
//        user.setName("admin");
//        user.setGender(Gender.unspecified);
//        user.setPassword(Password.hash("123456"));
//        user.setEmail("admin@seu.edu.cn");
//        user.setRoles(new String[]{"admin"});
//        database.persist(user);
//        tx.commit();

        NettyServer server = new NettyServer(9090);
        server.run(router, database);
    }
}