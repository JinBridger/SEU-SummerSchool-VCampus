package app.vcampus.client;

import app.vcampus.client.net.NettyClient;
import io.netty.channel.Channel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Application {
    private static final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NettyClient client = new NettyClient("127.0.0.1", 9090);
        Future<Channel> future = executorService.submit(client);
        Channel channel = future.get();

//        System.out.println("Hello, world!");
        EntryKt.main();
//        EntryKt.dialog();
    }
}
