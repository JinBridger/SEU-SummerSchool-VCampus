package app.vcampus.server;

import app.vcampus.server.controller.AuthController;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.router.Router;

public class Main {
    public static void main(String[] args) throws Exception {
        // 初始化路由
        Router router = new Router();
        router.addController(AuthController.class);

        // 启动 Netty 作为 Socket 服务端
        NettyServer server = new NettyServer(9090);
        server.run(router);
    }
}