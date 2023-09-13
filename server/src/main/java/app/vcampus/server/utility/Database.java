package app.vcampus.server.utility;

import app.vcampus.server.entity.*;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Database utility class.
 */
public class Database {
    /**
     * Initialize the database SessionFactory.
     *
     * @return The SessionFactory.
     */
    public static SessionFactory init() {
        Configuration configuration = new Configuration().configure();
        return configuration
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(LibraryBook.class)
                .addAnnotatedClass(TeachingClass.class)
                .addAnnotatedClass(StoreItem.class)
                .addAnnotatedClass(StoreTransaction.class)
                .addAnnotatedClass(SelectRecord.class)
                .addAnnotatedClass(FinanceCard.class)
                .addAnnotatedClass(CardTransaction.class)
                .addAnnotatedClass(TeachingEvaluation.class)
                .addAnnotatedClass(LibraryTransaction.class)
                .buildSessionFactory();
    }

    /**
     * Load all data of a type from the database.
     *
     * @param type The Entity of the data.
     * @param session The database session.
     * @return The list of data.
     * @param <T> The type of the data.
     */
    public static <T> List<T> loadAllData(Class<T> type, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return session.createQuery(criteria).getResultList();
    }

    /**
     * Load all data of a type with a like condition from the database.
     *
     * @param type The Entity of the data.
     * @param field The field to query.
     * @param value The value to query.
     * @param session The database session.
     * @return The list of data.
     * @param <T> The type of the data.
     */
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

    /**
     * Load all data of a type with a String where condition from the database.
     *
     * @param type The Entity of the data.
     * @param field The field to query.
     * @param value The value to query.
     * @param session The database session.
     * @return The list of data.
     * @param <T> The type of the data.
     */
    public static <T> List<T> getWhereString(Class<T> type, String field, String value, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> itemRoot = criteria.from(type);
        criteria.where(builder.equal(itemRoot.get(field).as(String.class), value));
        return session.createQuery(criteria).getResultList();
    }

    /**
     * Load all data of a type with a UUID where condition from the database.
     *
     * @param type The Entity of the data.
     * @param field The field to query.
     * @param value The value to query.
     * @param session The database session.
     * @return The list of data.
     * @param <T> The type of the data.
     */
    public static <T> List<T> getWhereUuid(Class<T> type, String field, UUID value, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> itemRoot = criteria.from(type);
        criteria.where(builder.equal(itemRoot.get(field), value));
        return session.createQuery(criteria).getResultList();
    }

    /**
     * Update all data of a type with a String where condition in the database.
     *
     * @param type The Entity of the data.
     * @param field The field to query.
     * @param value The value to query.
     * @param updates The updates to perform.
     * @param session The database session.
     * @param <T> The type of the data.
     */
    public static <T> void updateWhere(Class<T> type, String field, String value, List<Pair<String, String>> updates, Session session) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(type);
        Root<T> itemRoot = criteria.from(type);

        criteria.where(builder.equal(itemRoot.get(field).as(String.class), value));
        updates.forEach(pair -> criteria.set(pair.getFirst(), pair.getSecond()));

        session.createMutationQuery(criteria).executeUpdate();
    }
}
