package app.vcampus.client;

import app.vcampus.client.net.NettyClient;
import app.vcampus.client.net.NettyHandler;
import app.vcampus.client.utility.Request;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Application {
    private static final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1", 9090);
        Future<NettyHandler> future = executorService.submit(client);
        NettyHandler handler = future.get();

        Request request = new Request();
        request.setUri("heartbeat");
        handler.sendRequest(request, response -> {
            System.out.println(response.toString());
        });

        EntryKt.main();
    }
}
