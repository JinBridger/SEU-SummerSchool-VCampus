package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class BaseClient {
    public static Response sendRequest(NettyHandler handler, Request request) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference<>();
        log.debug("Sending request: {}", request);
        handler.sendRequest(request, res -> {
            log.debug("Response received: {}", res);
            response.set(res);
            latch.countDown();
        });

        latch.await();

        return response.get();
    }

    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }
}
