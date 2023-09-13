package app.vcampus.server.controller;

import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.LibraryTransaction;
import app.vcampus.server.entity.User;
import app.vcampus.server.enums.BookStatus;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class LibraryBookController {

    /**
     * deal with client request for book adding in library
     * @param request from client with uri and role
     * @param database database
     * @return response, ok or bad request
     */
    @RouteMapping(uri = "library/addBook", role = "library_staff")
    public Response addBook(Request request, org.hibernate.Session database) {
        LibraryBook newBook = IEntity.fromJson(request.getParams().get("book"), LibraryBook.class);
        if (newBook == null) {
            return Response.Common.badRequest();
        }

        newBook.setUuid(UUID.randomUUID());
        Transaction tx = database.beginTransaction();
        Database.updateWhere(LibraryBook.class, "isbn", newBook.getIsbn(), List.of(
                new Pair<>("name", newBook.getName()),
                new Pair<>("description", newBook.getDescription()),
                new Pair<>("author", newBook.getAuthor()),
                new Pair<>("press", newBook.getPress()),
                new Pair<>("cover", newBook.getCover())
        ), database);
        database.persist(newBook);
        tx.commit();

        return Response.Common.ok();
    }

    /**
     * deal with client request of book delete in library, however not used
     * @param request client request with uri and role
     * @param database database
     * @return response, ok or error
     */
    @RouteMapping(uri = "library/deleteBook", role = "library_staff")
    public Response deleteBook(Request request, org.hibernate.Session database) {
        String id = request.getParams().get("uuid");

        if (id == null) return Response.Common.error("Book UUID cannot be empty");

        UUID uuid = UUID.fromString(id);
        LibraryBook toDelete = database.get(LibraryBook.class, uuid);
        if (toDelete == null) return Response.Common.error("No such book");

        Transaction tx = database.beginTransaction();
        database.remove(toDelete);
        tx.commit();

        return Response.Common.ok();
    }

    /**
     * deal with client request of book borrow in library
     * @param request client request with uri and role
     * @param database database
     * @return response, ok or error
     */
    @RouteMapping(uri = "library/borrowBook", role = "library_staff")
    public Response borrowBook(Request request, org.hibernate.Session database) {
        try {
            String bookUuid = request.getParams().get("bookUuid");
            int cardNumber = Integer.parseInt(request.getParams().get("cardNumber"));
            if (bookUuid == null || cardNumber == 0)
                return Response.Common.error("Book UUID or user UUID cannot be empty");

            UUID uuid = UUID.fromString(bookUuid);
            LibraryBook toBorrow = database.get(LibraryBook.class, uuid);
            if (toBorrow == null) return Response.Common.error("No such book");
            if (toBorrow.getBookStatus() != BookStatus.available) return Response.Common.error("Book is not available");

            User user = database.get(User.class, cardNumber);
            if (user == null) return Response.Common.error("No such user");

            Transaction tx = database.beginTransaction();
            toBorrow.setBookStatus(BookStatus.lend);
            database.persist(toBorrow);

            LibraryTransaction newRecord = new LibraryTransaction();
            newRecord.setUuid(UUID.randomUUID());
            newRecord.setUserId(cardNumber);
            newRecord.setBookUuid(uuid);
            newRecord.setBorrowTime(new Date());
            newRecord.setDueTime(Date.from(newRecord.getBorrowTime().toInstant().plusSeconds(60 * 60 * 24 * 30)));
            database.persist(newRecord);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error("Failed to borrow book");
        }
    }

    /**
     * deal with client request of book information update in library, the role is staff
     * @param request client request with uri and role
     * @param database database
     * @return response, ok or bad request
     */
    @RouteMapping(uri = "library/updateBook", role = "library_staff")
    public Response updateBook(Request request, org.hibernate.Session database) {
        LibraryBook newBook = IEntity.fromJson(request.getParams().get("book"), LibraryBook.class);
        LibraryBook toUpdate = database.get(LibraryBook.class, newBook.getUuid());
        if (toUpdate == null) {
            return Response.Common.badRequest();
        }

        Transaction tx = database.beginTransaction();
        toUpdate.setName(newBook.getName());
        toUpdate.setDescription(newBook.getDescription());
        toUpdate.setPlace(newBook.getPlace());
        toUpdate.setCover(newBook.getCover());
        toUpdate.setPress(newBook.getPress());
        toUpdate.setAuthor(newBook.getAuthor());
        toUpdate.setBookStatus(newBook.getBookStatus());
        database.persist(toUpdate);
        tx.commit();

        return Response.Common.ok();
    }

    /**
     * deal with client request of book search in library
     *      use keyword params to do like query
     * @param request from client with uri and role
     * @param database database
     * @return response, error or ok
     */
    @RouteMapping(uri = "library/searchBook")
    public Response searchBook(Request request, org.hibernate.Session database) {
        try {
            String keyword = request.getParams().get("keyword");
            if (keyword == null) return Response.Common.error("Keyword cannot be empty");
            List<LibraryBook> books = Database.likeQuery(LibraryBook.class, new String[]{"name", "isbn", "author", "description", "press"}, keyword, database);

            return Response.Common.ok(books.stream().collect(Collectors.groupingBy(w -> w.isbn)).entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream().map(LibraryBook::toJson).collect(Collectors.toList())
            )));
        } catch (Exception e) {
            return Response.Common.error("Failed to search books");
        }
    }

    @RouteMapping(uri = "library/isbn", role = "library_staff")
    public Response isbn(Request request, org.hibernate.Session database) {
        String isbn = request.getParams().get("isbn");

        if (isbn == null) return Response.Common.error("ISBN cannot be empty");

        List<LibraryBook> searchedBook = Database.getWhereString(LibraryBook.class, "isbn", isbn, database);
        if (!searchedBook.isEmpty()) return Response.Common.ok(Map.of("book", searchedBook.get(0).toJson()));

        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("http://47.99.80.202:6066/openApi/getInfoByIsbn?appKey=ae1718d4587744b0b79f940fbef69e77&isbn=" + isbn))
                    .GET()
                    .build();

            HttpResponse result = HttpClient.newHttpClient().send(httpRequest, java.net.http.HttpResponse.BodyHandlers.ofString());
            log.info(result.toString());
            Type type = new TypeToken<Map<String, Object>>() {
            }.getType();
            Map<String, Object> data = (new Gson()).fromJson(result.body().toString(), type);
            data = (Map<String, Object>) data.get("data");
            LibraryBook newBook = LibraryBook.fromWeb(data);
            return Response.Common.ok(Map.of("book", newBook.toJson()));
        } catch (Exception e) {
            log.warn("Fail to get book info", e);
            return Response.Common.error("Failed to get book info");
        }
    }

    @RouteMapping(uri = "library/user/records", role = "library_user")
    public Response userRecords(Request request, org.hibernate.Session database) {
        try {
            int cardNumber = request.getSession().getCardNum();

            List<LibraryTransaction> records = Database.getWhereString(LibraryTransaction.class, "userId", Integer.toString(cardNumber), database);
            records = records.stream().peek(w -> w.setBook(database.get(LibraryBook.class, w.getBookUuid()))).collect(Collectors.toList());
            records.sort((a, b) -> b.getBorrowTime().compareTo(a.getBorrowTime()));
            return Response.Common.ok(records.stream().map(LibraryTransaction::toJson).collect(Collectors.toList()));
        } catch (Exception e) {
            return Response.Common.error("Failed to get user records");
        }
    }

    @RouteMapping(uri = "library/user/renew", role = "library_user")
    public Response renew(Request request, org.hibernate.Session database) {
        try {
            String uuid = request.getParams().get("uuid");
            if (uuid == null) return Response.Common.error("UUID cannot be empty");

            UUID bookUuid = UUID.fromString(uuid);
            LibraryTransaction toRenew = database.get(LibraryTransaction.class, bookUuid);
            if (toRenew == null) return Response.Common.error("No such record");

            if (toRenew.getUserId() != request.getSession().getCardNum())
                return Response.Common.error("You cannot renew this book");

            LibraryBook book = database.get(LibraryBook.class, toRenew.getBookUuid());
            if (book == null) return Response.Common.error("No such book");

            if (toRenew.getReturnTime() != null) return Response.Common.error("Book has been returned");
            if (toRenew.getDueTime().before(new Date())) return Response.Common.error("Book is overdue");

            Transaction tx = database.beginTransaction();
            toRenew.setDueTime(Date.from(toRenew.getDueTime().toInstant().plusSeconds(60 * 60 * 24 * 30)));
            database.persist(toRenew);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error("Failed to renew book");
        }
    }

    @RouteMapping(uri = "library/staff/records", role = "library_staff")
    public Response staffRecords(Request request, org.hibernate.Session database) {
        try {
            int cardNumber = Integer.parseInt(request.getParams().get("cardNumber"));

            List<LibraryTransaction> records = Database.getWhereString(LibraryTransaction.class, "userId", Integer.toString(cardNumber), database);
            records = records.stream().peek(w -> w.setBook(database.get(LibraryBook.class, w.getBookUuid()))).collect(Collectors.toList());
            records.sort((a, b) -> b.getBorrowTime().compareTo(a.getBorrowTime()));
            return Response.Common.ok(records.stream().map(LibraryTransaction::toJson).collect(Collectors.toList()));
        } catch (Exception e) {
            return Response.Common.error("Failed to get staff records");
        }
    }

    @RouteMapping(uri = "library/staff/renew", role = "library_staff")
    public Response staffRenew(Request request, org.hibernate.Session database) {
        try {
            String uuid = request.getParams().get("uuid");
            if (uuid == null) return Response.Common.error("UUID cannot be empty");

            UUID bookUuid = UUID.fromString(uuid);
            LibraryTransaction toRenew = database.get(LibraryTransaction.class, bookUuid);
            if (toRenew == null) return Response.Common.error("No such record");

            LibraryBook book = database.get(LibraryBook.class, toRenew.getBookUuid());
            if (book == null) return Response.Common.error("No such book");

            if (toRenew.getReturnTime() != null) return Response.Common.error("Book has been returned");

            Transaction tx = database.beginTransaction();
            toRenew.setDueTime(Date.from(toRenew.getDueTime().toInstant().plusSeconds(60 * 60 * 24 * 30)));
            database.persist(toRenew);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error("Failed to renew book");
        }
    }

    @RouteMapping(uri = "library/staff/return", role = "library_staff")
    public Response returnBook(Request request, org.hibernate.Session database) {
        try {
            String uuid = request.getParams().get("uuid");
            if (uuid == null) return Response.Common.error("UUID cannot be empty");

            UUID bookUuid = UUID.fromString(uuid);
            LibraryTransaction toReturn = database.get(LibraryTransaction.class, bookUuid);
            if (toReturn == null) return Response.Common.error("No such record");

            if (toReturn.getReturnTime() != null) return Response.Common.error("Book has been returned");

            LibraryBook book = database.get(LibraryBook.class, toReturn.getBookUuid());
            if (book == null) return Response.Common.error("No such book");

            Transaction tx = database.beginTransaction();
            toReturn.setReturnTime(new Date());
            book.setBookStatus(BookStatus.available);
            database.persist(toReturn);
            database.persist(book);
            tx.commit();

            return Response.Common.ok();
        } catch (Exception e) {
            return Response.Common.error("Failed to return book");
        }
    }
}
