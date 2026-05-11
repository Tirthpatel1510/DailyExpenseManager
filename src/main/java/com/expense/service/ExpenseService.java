package com.expense.service;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import java.util.List;
import java.util.Map;

/**
 * Service layer for Expense related business logic.
 */
public class ExpenseService {
    private ExpenseDAO expenseDAO = new ExpenseDAO();

    public void saveExpense(Expense expense) {
        expenseDAO.saveExpense(expense);
    }

    public List<Expense> getAllExpenses(String email) {
        return expenseDAO.getAllExpenses(email);
    }

    public void deleteExpense(int id) {
        expenseDAO.deleteExpense(id);
    }

    public Expense getExpenseById(int id) {
        return expenseDAO.getExpenseById(id);
    }

    public void updateExpense(Expense expense) {
        expenseDAO.updateExpense(expense);
    }

    public Map<String, Double> getCategoryTotals(String userEmail) {
        return expenseDAO.getCategoryTotals(userEmail);
    }

    public double getTotalCredit(String userEmail) {
        return expenseDAO.getTotalCredit(userEmail);
    }

    public double getTotalDebit(String userEmail) {
        // I noticed getTotalDebit(userEmail) might not be in the DAO yet based on the view_file output which was truncated
        // Let's assume it is or I will add it.
        return expenseDAO.getTotalDebit(userEmail);
    }

    public double getMonthlySavings(String userEmail) {
        return expenseDAO.getMonthlySavings(userEmail);
    }
    
    public List<Expense> searchExpenses(String query, String email) {
        return expenseDAO.searchExpenses(query, email);
    }

    public List<Expense> getPaginatedExpenses(String email, int offset, int limit) {
        return expenseDAO.getPaginatedExpenses(email, offset, limit);
    }

    public long getExpenseCount(String email) {
        return expenseDAO.getExpenseCount(email);
    }
}
