package app.vcampus.server.controller;


import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.User;
import app.vcampus.server.utility.Request;
import app.vcampus.server.utility.Response;
import app.vcampus.server.utility.router.RouteMapping;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Transaction;

import java.util.concurrent.TransferQueue;

@Slf4j
public class TeachingAffairsController {
    @RouteMapping(uri = "course/addCourse")
    public Response addCourse(Request request, org.hibernate.Session database)
    {
        Course newCourse=Course.fromMap(request.getParams());
        if(newCourse==null)
        {
            return Response.Common.badRequest();
        }

        Course user=database.get(Course.class,newCourse.getCourseId());
        if(user==null){
            return Response.Common.error("Course not found");
        }

        Transaction tx=database.beginTransaction();
        database.persist(newCourse);
        tx.commit();

        return Response.Common.ok();
    }








}
