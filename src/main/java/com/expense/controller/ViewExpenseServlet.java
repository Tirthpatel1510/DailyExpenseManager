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

        request.getRequestDispatcher(
                "views/expenses.jsp")
                .forward(request, response);
    }
}
