package com.expense.dao;

import com.expense.model.Expense;
import com.expense.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

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

    public List<Expense> getAllExpenses() {

        Session session = null;

        List<Expense> expenses = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            expenses = session
                    .createQuery("from Expense", Expense.class)
                    .list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {
                session.close();
            }
        }

        return expenses;
    }

    public void deleteExpense(int id) {

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            transaction = session.beginTransaction();

            Expense expense = session.get(Expense.class, id);

            if (expense != null) {

                session.delete(expense);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {
                transaction.rollback();
            }

            e.printStackTrace();

        } finally {

            if (session != null) {
                session.close();
            }
        }
    }

    public List<Expense> getExpensesByCategory(String category) {

        Session session = null;
        List<Expense> expenses = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            Query<Expense> query = session.createQuery(
                    "FROM Expense WHERE category = :cat",
                    Expense.class);

            query.setParameter("cat", category);

            expenses = query.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return expenses;
    }
}
