package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.client.utility.Request;
import app.vcampus.client.utility.Response;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class AuthClient {
    public static boolean login(NettyHandler handler, String username, String password) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("heartbeat");
        handler.sendRequest(request, res -> {
            // sleep 1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(res.toString());
            response.set(res);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
