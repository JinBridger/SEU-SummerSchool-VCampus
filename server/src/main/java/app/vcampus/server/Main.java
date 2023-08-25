package app.vcampus.server;

import app.vcampus.server.controller.AuthController;
import app.vcampus.server.controller.IndexController;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.router.Router;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws Exception {
        // 初始化路由
        Router router = new Router();
        router.addController(IndexController.class);
        router.addController(AuthController.class);

        // 初始化数据库 Session
        Session database = Database.init();

//        Transaction tx = database.beginTransaction();
//        User user = new User();
//        user.setCardNum(0);
//        user.setName("管理员");
//        user.setGender(Gender.unspecified);
//        user.setPassword(Password.hash("123456"));
//        user.setEmail("admin@seu.edu.cn");
//        user.setRoles(new String[]{"admin"});
//        database.persist(user);
//        tx.commit();

        // 启动 Netty 作为 Socket 服务端
        NettyServer server = new NettyServer(9090);
        server.run(router, database);
    }
}