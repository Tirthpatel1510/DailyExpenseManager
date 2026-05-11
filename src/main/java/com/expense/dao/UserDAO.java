package com.expense.dao;

import com.expense.model.User;
import com.expense.utility.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Data Access Object for User related database operations.
 */
public class UserDAO {
    /**
     * Authenticates a user by email and password.
     * @param email User email
     * @param password Plain text password
     * @return User object if authenticated, null otherwise
     */
    public User login(String email, String password) {

        User user = getUserByEmail(email);

        if (user != null && user.getPassword() != null && user.getPassword().startsWith("$2")) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            }
        }

        return null;
    }

    public User getUserByEmail(String email) {

        Session session = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Query<User> query = session.createQuery(
                    "FROM User WHERE email=:email",
                    User.class);

            query.setParameter("email", email);

            return query.uniqueResult();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {
                session.close();
            }
        }

        return null;
    }

    public boolean register(User user) {

        Session session = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }

            return false;

        } finally {

            if (session != null) {
                session.close();
            }
        }
    }

    public boolean registerUser(User user) {

        Transaction tx = null;

        try {

            Session session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            tx = session.beginTransaction();

            session.save(user);

            tx.commit();

            session.close();

            return true;

        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            e.printStackTrace();
        }

        return false;
    }

    public void updateUser(User user) {
        Transaction tx = null;
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            session.close();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
}
