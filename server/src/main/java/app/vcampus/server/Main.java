package app.vcampus.server;

import app.vcampus.server.controller.AuthController;
import app.vcampus.server.controller.IndexController;
import app.vcampus.server.controller.StudentStatusController;
import app.vcampus.server.enums.Polistat;
import app.vcampus.server.enums.Status;
import app.vcampus.server.net.NettyServer;
import app.vcampus.server.utility.Database;
import app.vcampus.server.utility.router.Router;
import org.hibernate.Session;
import app.vcampus.server.entity.User;
import app.vcampus.server.entity.Student;
import app.vcampus.server.utility.Password;
import app.vcampus.server.enums.Gender;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) throws Exception {
        Router router = new Router();
//        router.addController(AuthController.class);
        router.addController(IndexController.class);
        router.addController(StudentStatusController.class);


        Session database = Database.init();

//        Transaction tx = database.beginTransaction();
//        Student student = new Student();
//        String cardNumber = "250";
//        student.setCardNumber(Integer.valueOf(cardNumber));
//        student.setMajor(1);
//        student.setSchool(901);
//        database.persist(student);
//        tx.commit();

        NettyServer server = new NettyServer(9090);
        server.run(router, database);
    }
}