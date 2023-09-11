package app.vcampus.client.gateway;

import app.vcampus.client.net.NettyHandler;
import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.LibraryTransaction;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public static Map<String, List<LibraryBook>> searchBook(NettyHandler handler, String keyword) {
        Request request = new Request();
        request.setUri("library/searchBook");
        request.setParams(Map.of(
                "keyword", keyword
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                Map<String, List<String>> raw_data = (Map<String, List<String>>) response.getData();
                Map<String, List<LibraryBook>> data = new HashMap<>();
                raw_data.forEach((key, value) -> data.put(key, value.stream().map(json -> IEntity.fromJson(json, LibraryBook.class)).toList()));
                return data;
            } else {
                throw new RuntimeException("Failed to get book info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get book info", e);
            return null;
        }
    }

    public static boolean updateBook(NettyHandler handler, LibraryBook book) {
        Request request = new Request();
        request.setUri("library/updateBook");
        request.setParams(Map.of(
                "book", book.toJson()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to update book", e);
            return false;
        }
    }

    public static boolean deleteBook(NettyHandler handler, UUID uuid) {
        Request request = new Request();
        request.setUri("library/deleteBook");
        request.setParams(Map.of(
                "uuid", uuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to delete book", e);
            return false;
        }
    }

    public static boolean borrowBook(NettyHandler handler, String bookUuid, String cardNumber) {
        Request request = new Request();
        request.setUri("library/borrowBook");
        request.setParams(Map.of(
                "bookUuid", bookUuid,
                "cardNumber", cardNumber
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to borrow book", e);
            return false;
        }
    }

    public static List<LibraryTransaction> getMyRecords(NettyHandler handler) {
        Request request = new Request();
        request.setUri("library/user/records");

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                List<String> raw_data = (List<String>) response.getData();
                return raw_data.stream().map(json -> IEntity.fromJson(json, LibraryTransaction.class)).toList();
            } else {
                throw new RuntimeException("Failed to get book info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get book info", e);
            return null;
        }
    }

    public static List<LibraryTransaction> staffGetRecords(NettyHandler handler, String cardNumber) {
        Request request = new Request();
        request.setUri("library/staff/records");
        request.setParams(Map.of(
                "cardNumber", cardNumber
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            if (response.getStatus().equals("success")) {
                List<String> raw_data = (List<String>) response.getData();
                return raw_data.stream().map(json -> IEntity.fromJson(json, LibraryTransaction.class)).toList();
            } else {
                throw new RuntimeException("Failed to get book info");
            }
        } catch (InterruptedException e) {
            log.warn("Fail to get book info", e);
            return null;
        }
    }

    public static Boolean userRenewBook(NettyHandler handler, UUID uuid) {
        Request request = new Request();
        request.setUri("library/user/renew");
        request.setParams(Map.of(
                "uuid", uuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to renew book", e);
            return false;
        }
    }

    public static Boolean staffRenewBook(NettyHandler handler, UUID uuid) {
        Request request = new Request();
        request.setUri("library/staff/renew");
        request.setParams(Map.of(
                "uuid", uuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to renew book", e);
            return false;
        }
    }

    public static Boolean returnBook(NettyHandler handler, UUID uuid) {
        Request request = new Request();
        request.setUri("library/staff/return");
        request.setParams(Map.of(
                "uuid", uuid.toString()
        ));

        try {
            Response response = BaseClient.sendRequest(handler, request);
            return response.getStatus().equals("success");
        } catch (InterruptedException e) {
            log.warn("Fail to return book", e);
            return false;
        }
    }
}
