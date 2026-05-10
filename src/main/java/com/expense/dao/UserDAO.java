package com.expense.dao;

import com.expense.model.User;
import com.expense.utility.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO {

    public boolean validateUser(String username,
            String password) {

        Session session = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Query<User> query = session.createQuery(
                    "FROM User WHERE username=:u AND password=:p",
                    User.class);

            query.setParameter("u", username);
            query.setParameter("p", password);

            User user = query.uniqueResult();

            return user != null;

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {
                session.close();
            }
        }

        return false;
    }
}
