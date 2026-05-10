package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/viewExpenses")
public class ViewExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        ExpenseDAO expenseDAO = new ExpenseDAO();

        List<Expense> expenses = expenseDAO.getAllExpenses();

        List<String> categories = expenseDAO.getAllCategories();

        request.setAttribute("expenses", expenses);

        request.setAttribute(
                "categories",
                categories);

        Map<String, Double> categoryTotals = expenseDAO.getCategoryTotals();

        request.setAttribute(
                "categoryTotals",
                categoryTotals);

        double totalCredit = expenseDAO.getTotalCredit();

        double totalDebit = expenseDAO.getTotalDebit();

        double balance = totalCredit - totalDebit;

        request.setAttribute(
                "totalCredit",
                totalCredit);

        request.setAttribute(
                "totalDebit",
                totalDebit);

        request.setAttribute(
                "balance",
                balance);

        double monthlySavings = expenseDAO.getMonthlySavings();

        request.setAttribute(
                "monthlySavings",
                monthlySavings);

        List<Object[]> trends = expenseDAO.getMonthlyExpenseTrends();

        request.setAttribute(
                "trends",
                trends);

        Object[] highestCategory = expenseDAO
                .getHighestSpendingCategory();

        request.setAttribute(
                "highestCategory",
                highestCategory);

        request.getRequestDispatcher(
                "views/expenses.jsp")
                .forward(request, response);
    }
}
