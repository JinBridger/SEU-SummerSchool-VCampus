package app.vcampus.server.utility;

import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.LibraryBook;
import app.vcampus.server.entity.Student;
import app.vcampus.server.entity.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class Database {
    public static Session init() {
        Configuration configuration = new Configuration().configure();
        return configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(LibraryBook.class)
                .buildSessionFactory().openSession();
    }
}
