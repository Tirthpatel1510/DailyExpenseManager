package com.expense.dao;

import com.expense.model.Expense;
import com.expense.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

    public List<Expense> getAllExpenses(String email) {

        Session session = null;

        List<Expense> expenses = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Query<Expense> query = session.createQuery(
                    "FROM Expense WHERE userEmail = :email",
                    Expense.class);

            query.setParameter("email", email);

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

    public Expense getExpenseById(int id) {

        Session session = null;
        Expense expense = null;

        try {

            session = HibernateUtil.getSessionFactory().openSession();

            expense = session.get(Expense.class, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return expense;
    }

    public void updateExpense(Expense expense) {

        Session session = null;
        Transaction transaction = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            transaction = session.beginTransaction();

            session.update(expense);

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

    public Map<String, Double> getCategoryTotals() {

        Session session = null;

        Map<String, Double> categoryTotals = new HashMap<>();

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            List<Object[]> results = session.createQuery(
                    "SELECT category, SUM(amount) " +
                            "FROM Expense GROUP BY category")
                    .list();

            for (Object[] row : results) {

                String category = (String) row[0];

                Double total = (Double) row[1];

                categoryTotals.put(category, total);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return categoryTotals;
    }

    public List<String> getAllCategories() {

        Session session = null;

        List<String> categories = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            categories = session.createQuery(
                    "SELECT DISTINCT category FROM Expense",
                    String.class)
                    .list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return categories;
    }

    public double getTotalCredit() {

        Session session = null;

        double total = 0;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Double result = session.createQuery(
                    "SELECT SUM(amount) " +
                            "FROM Expense " +
                            "WHERE type='Credit'",
                    Double.class)
                    .uniqueResult();

            if (result != null) {

                total = result;
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return total;
    }

    public double getMonthlySavings() {

        Session session = null;

        double credit = 0;
        double debit = 0;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Double creditResult = session.createQuery(

                    "SELECT SUM(amount) " +
                            "FROM Expense " +
                            "WHERE type='Credit' " +
                            "AND MONTH(expenseDate)=MONTH(CURRENT_DATE()) " +
                            "AND YEAR(expenseDate)=YEAR(CURRENT_DATE())",

                    Double.class)

                    .uniqueResult();

            Double debitResult = session.createQuery(

                    "SELECT SUM(amount) " +
                            "FROM Expense " +
                            "WHERE type='Debit' " +
                            "AND MONTH(expenseDate)=MONTH(CURRENT_DATE()) " +
                            "AND YEAR(expenseDate)=YEAR(CURRENT_DATE())",

                    Double.class)

                    .uniqueResult();

            if (creditResult != null) {

                credit = creditResult;
            }

            if (debitResult != null) {

                debit = debitResult;
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return credit - debit;
    }

    public List<Object[]> getMonthlyExpenseTrends() {

        Session session = null;

        List<Object[]> results = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            results = session.createQuery(

                    "SELECT MONTH(expenseDate), " +
                            "SUM(amount) " +

                            "FROM Expense " +

                            "WHERE type='Debit' " +

                            "GROUP BY MONTH(expenseDate)")

                    .list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return results;
    }

    public Object[] getHighestSpendingCategory() {

        Session session = null;

        Object[] result = null;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            result = (Object[])

            session.createQuery(

                    "SELECT category, SUM(amount) " +

                            "FROM Expense " +

                            "WHERE type='Debit' " +

                            "GROUP BY category " +

                            "ORDER BY SUM(amount) DESC")

                    .setMaxResults(1)

                    .uniqueResult();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return result;
    }

    public double getTotalDebit() {

        Session session = null;

        double total = 0;

        try {

            session = HibernateUtil
                    .getSessionFactory()
                    .openSession();

            Double result = session.createQuery(
                    "SELECT SUM(amount) " +
                            "FROM Expense " +
                            "WHERE type='Debit'",
                    Double.class)
                    .uniqueResult();

            if (result != null) {

                total = result;
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (session != null) {

                session.close();
            }
        }

        return total;
    }
}
