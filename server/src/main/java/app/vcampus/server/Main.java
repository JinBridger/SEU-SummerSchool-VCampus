package app.vcampus.server;

import app.vcampus.server.net.NettyServer;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws Exception {
        NettyServer server = new NettyServer(9090);
        server.run();
    }
}