package app.vcampus.server.controller;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeachingAffairsController {
//    @RouteMapping(uri = "course/addCourse")
//    public Response addCourse(Request request, org.hibernate.Session database) {
//        Course newCourse = Course.fromMap(request.getParams());
//        if (newCourse == null) {
//            return Response.Common.badRequest();
//        }
//
//        Course user = database.get(Course.class, newCourse.getCourseId());
//        if (user == null) {
//            return Response.Common.error("Course not found");
//        }
//
//        Transaction tx = database.beginTransaction();
//        database.persist(newCourse);
//        tx.commit();
//
//        return Response.Common.ok();
//    }
//
////    @RouteMapping(uri="class/searchClass")
////    public Response searchClass(Request request,org.hibernate.Session database)
////    {
////        String courseId=request.getParams().get("courseId");
////        if(courseId==null){
////            return Response.Common.error("course id could not be empty");
////        }
////
////        Class class
////    }
//
//    @RouteMapping(uri = "class/addClass")
//    public  Response addClass(Request request,org.hibernate.Session database)
//    {
//        TeachingClass newclass= TeachingClass.fromMap(request.getParams());
//        if(newclass==null)return Response.Common.badRequest();
//
//        User user=database.get(User.class,newclass.getCourseId());
//        if(user==null){
//            return Response.Common.error("not found");
//        }
//
//        Transaction tx=database.beginTransaction();
//        database.persist(newclass);
//        tx.commit();
//
//        return Response.Common.ok();
//    }

}
