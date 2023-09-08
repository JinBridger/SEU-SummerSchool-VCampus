package app.vcampus.server;

import app.vcampus.server.controller.*;
import app.vcampus.server.entity.*;
import app.vcampus.server.enums.TransactionType;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.DateUtility;
import app.vcampus.server.utility.router.Router;
import org.bouncycastle.util.Store;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.addController(AuthController.class);
        router.addController(IndexController.class);
        router.addController(StudentStatusController.class);
        router.addController(LibraryBookController.class);
        router.addController(StoreController.class);
        router.addController(TeachingAffairsController.class);
        router.addController(FinanceController.class);

        Session database = Database.init();

//        String text = formatter.format(date);
//        System.out.println(text);
        Transaction tx = database.beginTransaction();
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
//        Course course = new Course();
//        course.setUuid(UUID.fromString("e1386a64-dd0d-4422-967b-fdeadee68e30"));
//        course.setCourseId("BJSL0081");
//        course.setCourseName("软件工程");
//        course.setSchool("计算机科学与工程学院");
//        course.setCredit(4);
//        database.persist(course);
//        tx.commit();

//        SelectedClass selectedClass = new SelectedClass();
//        selectedClass.setUuid(UUID.randomUUID());
//        selectedClass.setCardNumber(123456);
//        selectedClass.setClassUuid(UUID.fromString("c30d2d57-8edc-4471-b143-4d2df741b6e8"));
//        selectedClass.setSelectTime(new Date());
//        database.persist(selectedClass);

//        SelectedClass selectedClass = database.get(SelectedClass.class, UUID.fromString("a010f8b1-ccfc-4900-98fd-72cb5b432dc0"));
//        selectedClass.setGrade(new Grades(100, 100, 100, 100));
//        database.persist(selectedClass);

//        CardTransaction cardTransaction = new CardTransaction();
//        cardTransaction.setUuid(UUID.randomUUID());
//        cardTransaction.setCardNumber(123456);
//        cardTransaction.setAmount(-100);
//        cardTransaction.setType(TransactionType.payment);
//        cardTransaction.setDescription("商店消费");
//        cardTransaction.setTime(new Date());
//        database.persist(cardTransaction);

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
//
//        StoreTransaction StoreTransaction = new StoreTransaction();
//        StoreTransaction.setUuid(UUID.randomUUID());
//        StoreTransaction.setItemUUID(UUID.randomUUID());
//        StoreTransaction.setItemPrice(100);
//        StoreTransaction.setAmount(10);
//        StoreTransaction.setCardNumber(123456);
//        String testString ="2023-09-04";
//        StoreTransaction.setTime(DateUtility.toDate(testString));
//        StoreTransaction.setRemark("test");
//        database.persist(StoreTransaction);

        tx.commit();
        NettyServer server = new NettyServer(9091);
        server.run(router, database);
    }
}