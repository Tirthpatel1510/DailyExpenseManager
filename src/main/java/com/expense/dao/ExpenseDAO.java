package com.expense.dao;

import com.expense.model.Expense;
import com.expense.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ExpenseDAO {

    public void saveExpense(Expense expense) {

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            session.save(expense);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();
        }
    }
}
