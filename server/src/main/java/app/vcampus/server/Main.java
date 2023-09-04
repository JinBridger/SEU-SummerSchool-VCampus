package app.vcampus.server;

import app.vcampus.server.controller.*;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.router.Router;
import org.hibernate.Session;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.addController(AuthController.class);
        router.addController(IndexController.class);
        router.addController(StudentStatusController.class);
        router.addController(LibraryBookController.class);
        router.addController(StoreController.class);

        Session database = Database.init();

//        String text = formatter.format(date);
//        System.out.println(text);
//        Transaction tx = database.beginTransaction();
//        User user = new User();
//        user.setCardNum(123456);
//        user.setName("admin");
//        user.setGender(Gender.unspecified);
//        user.setPassword(Password.hash("123456"));
//        user.setEmail("admin@seu.edu.cn");
//        user.setRoles(new String[]{"admin"});
//        user.setPhone("12345678901");
//        database.persist(user);
//        tx.commit();

//        Transaction tx = database.beginTransaction();
//        TeachingClass teachingClass = new TeachingClass();
//        teachingClass.setUuid(UUID.randomUUID());
//        teachingClass.setCourseUuid(UUID.randomUUID());
//        teachingClass.setSchedule(List.of(new Pair<>(new Pair<>(1, 16), new Pair<>(1, new Pair<>(1, 2)))));
//        teachingClass.setCourseName("软件工程");
//        teachingClass.setTeacherId(123456);
//        teachingClass.setPlace("东九楼");
//        teachingClass.setCapacity(100);
//        database.persist(teachingClass);
//        tx.commit();

//        Transaction tx = database.beginTransaction();
//        Student student =new Student();
//        student.setCardNumber(123456);
//        student.setStudentNumber("123456");
//        student.setStatus(Status.inSchool);
//        student.setPoliticalStatus(PoliticalStatus.Masses);
//        student.setGivenName("小明");
//        student.setFamilyName("王");
//        student.setBirthPlace("SEU");
//        String testString ="2023-09-04";
//        student.setBirthDate(DateUtility.toDate(testString));
//        student.setGender(Gender.male);
//        student.setMajor("计算机科学与技术");
//        student.setSchool("计算机科学与工程学院");
//        database.persist(student);
//        User user = new User();
//        user.setCardNum(123456);
//        user.setName("admin");
//        user.setGender(Gender.unspecified);
//        user.setPassword(Password.hash("123456"));
//        user.setEmail("admin@seu.edu.cn");
//        user.setRoles(new String[]{"admin"});
//        user.setPhone("12345678901");
//        database.persist(user);
//        tx.commit();
        NettyServer server = new NettyServer(9091);
        server.run(router, database);
    }
}