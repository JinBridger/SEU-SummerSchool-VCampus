package app.vcampus.server.controller;

import app.vcampus.server.entity.IEntity;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.LibraryTransaction;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class LibraryBookController {

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

    @RouteMapping(uri = "library/updateBook", role = "library_staff")
    public Response updateBook(Request request, org.hibernate.Session database) {
        /*
            when users borrow a book or the administrator update some books' information manually,
            some book information will be updated with this method
         */

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

        List<LibraryBook> searchedBook = Database.getWhere(LibraryBook.class, "isbn", isbn, database);
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

//    @RouteMapping(uri="library/getBookInfo")
//    public Response getBookInfo(Request request,org.hibernate.Session database){
//        /*
//        this method is used when user clicks the searched book to show the detailed book information
//         */
//        try {
//            String id=request.getParams().get("uuid");
//            if(id==null) return Response.Common.error("uuid cannot be empty");
//
//            UUID uuid=UUID.fromString(id);
//            LibraryBook book=database.get(LibraryBook.class,uuid);
//
//            if(book==null){
//                return Response.Common.error("missing book information");
//            }
//
//            return Response.Common.ok(book.toMap());
//        } catch (Exception e) {
//            return Response.Common.error("Fail to get book information");
//        }
//    }


    @RouteMapping(uri = "library/queryRecord", role = "library_staff")
    public Response queryBookTransaction(Request request, org.hibernate.Session database) {
        /*
            this method is used when the administrator query a book's transaction records
         */
        try {
            String keyword = request.getParams().get("keyword");
            if (keyword == null) return Response.Common.error("Keyword cannot be empty");
            List<LibraryTransaction> records = Database.likeQuery(LibraryTransaction.class, new String[]{"userId", "action", "time"}, keyword, database);

            return Response.Common.ok(records.stream().collect(Collectors.groupingBy(w -> w.userId)).entrySet().stream().collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream().map(LibraryTransaction::toJson).collect(Collectors.toList())
            )));
        } catch (Exception e) {
            return Response.Common.error("Failed to query book transaction records");
        }
    }

}
