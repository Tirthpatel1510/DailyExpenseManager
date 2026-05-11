package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {

            ExpenseDAO expenseDAO = new ExpenseDAO();

            List<Expense> expenses = expenseDAO.getAllExpenses(user.getEmail());

            String userEmail = user.getEmail();

            List<String> categories = expenseDAO.getAllCategories(userEmail);

            request.setAttribute("expenseList", expenses);
            request.setAttribute("expenses", expenses);

            request.setAttribute(
                    "categories",
                    categories);

            Map<String, Double> categoryTotals = expenseDAO.getCategoryTotals(userEmail);

            request.setAttribute(
                    "categoryTotals",
                    categoryTotals);

            double totalCredit = expenseDAO.getTotalCredit(userEmail);

            double totalDebit = expenseDAO.getTotalDebit(userEmail);

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

            double monthlySavings = expenseDAO.getMonthlySavings(userEmail);

            request.setAttribute(
                    "monthlySavings",
                    monthlySavings);

            List<Object[]> trends = expenseDAO.getMonthlyExpenseTrends(userEmail);

            request.setAttribute(
                    "trends",
                    trends);

            Object[] highestCategory = expenseDAO
                    .getHighestSpendingCategory(userEmail);

            request.setAttribute(
                    "highestCategory",
                    highestCategory);

            request.getRequestDispatcher(
                    "views/expenses.jsp")
                    .forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
