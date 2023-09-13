package app.vcampus.client;

import app.vcampus.client.net.NettyClient;
import app.vcampus.client.net.NettyHandler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Java Application class.
 */
public class Application {
    private static final ExecutorService executorService = java.util.concurrent.Executors.newCachedThreadPool();

    /**
     * Connect to a server.
     *
     * @param address The address of the server.
     * @param port The port of the server.
     * @return The netty handler.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static NettyHandler connect(String address, int port) throws ExecutionException, InterruptedException {
        NettyClient client = new NettyClient(address, port);
        Future<NettyHandler> future = executorService.submit(client);
        return future.get();
    }
}
