package app.vcampus.client;

import app.vcampus.client.net.NettyClient;
import app.vcampus.client.net.NettyHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Application {
    private static final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        NettyClient client = new NettyClient("127.0.0.1", 9091);
//        Future<NettyHandler> future = executorService.submit(client);
//        NettyHandler handler = future.get();
////
////        Request request = new Request();
////        request.setUri("heartbeat");
////        handler.sendRequest(request, response -> {
////            System.out.println(response.toString());
////        });
//
//        MainKt.main(handler);
//    }

    public static NettyHandler connect(String address, int port) throws ExecutionException, InterruptedException {
        NettyClient client = new NettyClient(address, port);
        Future<NettyHandler> future = executorService.submit(client);
        return future.get();
    }
}
