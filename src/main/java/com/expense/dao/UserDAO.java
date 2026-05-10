package com.expense.dao;

import com.expense.model.User;
import com.expense.utility.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO {
    public User login(String email, String password) {

        Session session = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Query<User> query = session.createQuery(
                    "FROM User WHERE email=:email AND password=:password",
                    User.class);

            query.setParameter("email", email);
            query.setParameter("password", password);

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
}
