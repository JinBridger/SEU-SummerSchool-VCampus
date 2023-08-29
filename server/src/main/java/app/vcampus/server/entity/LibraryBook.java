package app.vcampus.server.entity;

import app.vcampus.server.enums.BookStatus;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Entity
@Data
@Slf4j
@Table(name="book")
public class LibraryBook {
    @Id
    @Column(name="book_UID")
    public String UUID;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    public String ISBN;

    @Column(nullable = false)
    public String author;

    @Column(nullable = false)
    public String press;

    @Column(nullable = false)
    public String description;

    @Column(nullable = false)
    public String place;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public BookStatus bookStatus;

    public static LibraryBook fromrequest(Request request){
        try{
            String UUID= request.getParams().get("UUID");
            String name=request.getParams().get("name");
            String ISBN=request.getParams().get("ISBN");
            String author=request.getParams().get("author");
            String press=request.getParams().get("press");
            String description=request.getParams().get("description");
            String place=request.getParams().get("place");
            BookStatus bookStatus=BookStatus.valueOf(request.getParams().get("bookStatus"));

            LibraryBook book=new LibraryBook();
            book.setUUID(UUID);
            book.setName(name);
            book.setISBN(ISBN);
            book.setAuthor(author);
            book.setPress(press);
            book.setDescription(description);
            book.setPlace(place);
            book.setBookStatus(bookStatus);
            return book;

        }catch (Exception e) {
            log.warn("Failed to parse student from request", e);
            return null;
        }
    }

    public Response toResponse(){
        Response response=Response.Common.ok();
        response.setData(Map.of(
                "UUID",UUID,
                "name",name,
                "ISBN",ISBN,
                "author",author,
                "press",press,
                "description",description,
                "place",place,
                "bookStatus",bookStatus
        ));
        return response;
    }


}
