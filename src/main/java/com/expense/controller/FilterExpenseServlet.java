package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/filterExpenses")
public class FilterExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");

        ExpenseDAO expenseDAO = new ExpenseDAO();

        List<Expense> expenses;

        if (category == null || category.equals("") || category.equals("All")) {

            expenses = expenseDAO.getAllExpenses();

        } else {

            expenses = expenseDAO.getExpensesByCategory(category);
        }

        List<String> categories = expenseDAO.getAllCategories();

        Map<String, Double> categoryTotals = expenseDAO.getCategoryTotals();

        request.setAttribute("expenseList", expenses);
        request.setAttribute("expenses", expenses);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("categories", categories);
        request.setAttribute("categoryTotals", categoryTotals);

        RequestDispatcher rd = request.getRequestDispatcher(
                "views/expenses.jsp");

        rd.forward(request, response);
    }
}
