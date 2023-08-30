package app.vcampus.server.controller;

import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

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

}
