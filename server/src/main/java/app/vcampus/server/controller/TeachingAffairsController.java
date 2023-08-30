package app.vcampus.server.controller;

import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.SelectedClass;
import app.vcampus.server.entity.Student;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeachingAffairsController {
    @RouteMapping(uri = "course/addCourse")
    public Response addCourse(Request request, org.hibernate.Session database) {
        Course newCourse = Course.fromMap(request.getParams());
        if (newCourse == null) {
            return Response.Common.badRequest();
        }

        Course user = database.get(Course.class, newCourse.getCourseId());
        if (user == null) {
            return Response.Common.error("Course not found");
        }

        Transaction tx = database.beginTransaction();
        database.persist(newCourse);
        tx.commit();

        return Response.Common.ok();
    }

    /*This method is used to select courses for students*/
    @RouteMapping(uri="selectedClass/selectClass")
    public Response selectclass(Request request, org.hibernate.Session database) {
        SelectedClass newSelectedClass = SelectedClass.fromMap(request.getParams());
        if (newSelectedClass == null) {
            return Response.Common.badRequest();
        }
        SelectedClass selectedClass = database.get(SelectedClass.class, newSelectedClass.getCardNumber());
        if (selectedClass == null) {
            return Response.Common.error("SelectedClass not found");
        }

        Transaction tx = database.beginTransaction();
        database.persist(newSelectedClass);
        tx.commit();
        return Response.Common.ok();
    }


}
