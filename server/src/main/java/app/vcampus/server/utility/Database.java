package app.vcampus.server.utility;

import app.vcampus.server.entity.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static Session init() {
        Configuration configuration = new Configuration().configure();
        return configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(LibraryBook.class)
                .addAnnotatedClass(TeachingClass.class)
                .addAnnotatedClass(StoreItem.class)
                .addAnnotatedClass(StoreTransaction.class)
                .buildSessionFactory().openSession();
    }

    public static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return session.createQuery(criteria).getResultList();
    }

    public static <T> List<T> likeQuery(Class<T> type, String[] field, String value, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> itemRoot = criteria.from(type);
        ArrayList<Predicate> conditions = new ArrayList<>();
        for (String s : field) {
            conditions.add(builder.like(itemRoot.get(s).as(String.class), "%" + value + "%"));
        }
        criteria.where(builder.or(conditions.toArray(new Predicate[0])));
        return session.createQuery(criteria).getResultList();
    }

    public static <T> List<T> getWhere(Class<T> type, String field, String value, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> itemRoot = criteria.from(type);
        criteria.where(builder.equal(itemRoot.get(field).as(String.class), value));
        return session.createQuery(criteria).getResultList();
    }
}
