package app.vcampus.server.controller;

import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
public class LibraryBookController {

//    @RouteMapping(uri = "library/addBook")
//    public Response addBook(Request request, org.hibernate.Session database) {
//        LibraryBook newBook = LibraryBook.fromMap(request.getParams());
//        if (newBook == null) {
//            return Response.Common.badRequest();
//        }
//
//        Transaction tx = database.beginTransaction();
//        database.persist(newBook);
//        tx.commit();
//
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri = "library/deleteBook")
//    public Response deleteBook(Request request, org.hibernate.Session database) {
//        String UUID = request.getParams().get("UUID");
//
//        if (UUID == null) return Response.Common.error("Book UUID cannot be empty");
//
//        LibraryBook toDelete = database.get(LibraryBook.class, UUID);
//        if (toDelete == null) return Response.Common.error("No such book");
//
//        Transaction tx = database.beginTransaction();
//        database.remove(toDelete);
//        tx.commit();
//
//        return Response.Common.ok();
//    }
//
//    @RouteMapping(uri = "library/updateBook")
//    public Response updateBook(Request request, org.hibernate.Session database) {
//        LibraryBook newBook = LibraryBook.fromrequest(request);
//        if (newBook == null) return Response.Common.badRequest();
//
//        LibraryBook toUpdate = database.get(LibraryBook.class, request.getParams().get("UUID"));
//        if (toUpdate == null) return Response.Common.error("Incorrect book UUID");
//
//        if (newBook == toUpdate) return Response.Common.error("No update");
//
//        Transaction tx = database.beginTransaction();
//        toUpdate.setBookStatus(newBook.getBookStatus());
//        toUpdate.setDescription(newBook.getDescription());
//        toUpdate.setPlace(newBook.getPlace());
//        database.persist(toUpdate);
//        tx.commit();
//
//        return Response.Common.ok();
//
//    }
//
//    @RouteMapping(uri = "library/searchBook")
//    public Response searchBook(Request request, org.hibernate.Session database) {
//        String UUID = request.getParams().get("UUID");
//        if (UUID == null) return Response.Common.error("Book UUID cannot be empty");
//
//        LibraryBook searchedBook = database.get(LibraryBook.class, UUID);
//        if (searchedBook == null) return Response.Common.error("No such book");
//
//        System.out.println(searchedBook);
//
//        return searchedBook.toResponse();
//
//    }

    @RouteMapping(uri = "library/isbn")
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
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> data = (new Gson()).fromJson(result.body().toString(), type);
            data = (Map<String, Object>) data.get("data");
            LibraryBook newBook = LibraryBook.fromWeb(data);
            return Response.Common.ok(Map.of("book", newBook.toJson()));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.Common.error("Failed to get book info");
        }
    }
}
