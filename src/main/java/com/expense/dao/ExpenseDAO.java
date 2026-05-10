package com.expense.dao;

import com.expense.model.Expense;
import com.expense.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExpenseDAO {

    public void saveExpense(Expense expense) {

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            transaction = session.beginTransaction();

            session.save(expense);

            transaction.commit();

            System.out.println("Expense Saved Successfully!");

        } catch (Exception e) {

            if (transaction != null &&
                    transaction.isActive()) {

                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            if (session != null) {
                session.close();
            }
        }
    }
}
