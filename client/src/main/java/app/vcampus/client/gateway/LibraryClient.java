package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class LibraryClient {
    public static LibraryBook preAddBook(NettyHandler handler, String isbn) {
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Response> response = new AtomicReference<>();
        Request request = new Request();
        request.setUri("library/isbn");
        request.setParams(Map.of(
                "isbn", isbn
        ));
        handler.sendRequest(request, res -> {
            response.set(res);
            System.out.println(res);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        if (response.get().getStatus().equals("success")) {
            Map<String, String> data = (Map<String, String>) response.get().getData();
            return IEntity.fromJson(data.get("book"), LibraryBook.class);
        } else {
            return new LibraryBook();
        }
    }
}
