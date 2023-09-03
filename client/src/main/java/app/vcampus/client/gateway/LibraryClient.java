package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.Student;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class LibraryClient {
    public static LibraryBook preAddBook(NettyHandler handler, String isbn) {
        Request request = new Request();
        request.setUri("library/isbn");
        request.setParams(Map.of(
                "isbn", isbn
        ));
        try {
            Response response = BaseClient.sendRequest(handler, request);

            if (response.getStatus().equals("success")) {
                Map<String, String> data = (Map<String, String>) response.getData();
                return IEntity.fromJson(data.get("book"), LibraryBook.class);
            } else {
                throw new RuntimeException("Failed to get book info");
            }
        } catch (Exception e) {
            log.warn("Fail to get book info", e);
            return null;
        }
    }

    public static boolean addBook(NettyHandler handler, LibraryBook newBook) {
        Request request = new Request();
        request.setUri("library/addBook");
        request.setParams(Map.of(
                "book", newBook.toJson()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to add book", e);
            return false;
        }
    }

    public static LibraryBook getBookInfo(NettyHandler handler, UUID bookUuid){
        Request request = new Request();
        request.setUri("library/getBookInfo");
        request.setParams(Map.of("uuid", bookUuid.toString()));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                Map<String, String> data = (Map<String, String>) response.getData();
                return LibraryBook.fromMap(data);
            } else {
                throw new RuntimeException("Failed to get book info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get book info", e);
            return null;
        }
    }
}
