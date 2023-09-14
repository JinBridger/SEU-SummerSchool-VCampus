package app.vcampus.server;

import app.vcampus.server.controller.*;
import app.vcampus.server.entity.Course;
import app.vcampus.server.entity.TeachingClass;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.Pair;
import app.vcampus.server.utility.router.Router;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.UUID;

/**
 * Main class of the server.
 */
public class Main {

    /**
     * Entry function of the server.
     *
     * @param args Command line arguments.
     * @throws Exception Any exception that may occur.
     */
    public static void main(String[] args) throws Exception {
        Router router = new Router();
        router.addController(AuthController.class);
        router.addController(IndexController.class);
        router.addController(StudentStatusController.class);
        router.addController(LibraryBookController.class);
        router.addController(StoreController.class);
        router.addController(TeachingAffairsController.class);
        router.addController(FinanceController.class);
        router.addController(AdminController.class);

        SessionFactory databaseFactory = Database.init();
        org.hibernate.Session database = databaseFactory.openSession();

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

        Transaction tx = database.beginTransaction();
//        TeachingClass teachingClass = new TeachingClass();
//        teachingClass.setUuid(UUID.randomUUID());
//        teachingClass.setCourseUuid(UUID.randomUUID());
//        teachingClass.setSchedule(List.of(new Pair<>(new Pair<>(1, 16), new Pair<>(3, new Pair<>(1, 4)))));
//        teachingClass.setCourseName("Kotlin 101");
//        teachingClass.setTeacherId(123456);
//        teachingClass.setPlace("教八 303");
//        teachingClass.setCapacity(100);
//        database.persist(teachingClass);
//        tx.commit();

//        Transaction tx = database.beginTransaction();
//        Course course = new Course();
//        course.setUuid(UUID.randomUUID());
//        course.setCourseId("B09T1070");
//        course.setCourseName("计算机与社会");
//        course.setSchool("计算机科学与工程学院");
//        course.setCredit(2);
//        database.persist(course);
//        tx.commit();

//        TeachingClass teachingClass = new TeachingClass();
//        teachingClass.setUuid(UUID.randomUUID());
//        teachingClass.setCourseUuid(course.getUuid());
//        teachingClass.setSchedule(List.of(new Pair<>(new Pair<>(1, 16), new Pair<>(4, new Pair<>(3, 5)))));
//        teachingClass.setTeacherId(123456);
//        teachingClass.setPlace("教四-301");
//        teachingClass.setCapacity(60);
//        database.persist(teachingClass);

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
        server.run(router, databaseFactory);
    }
}