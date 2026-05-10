package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/filterExpenses")
public class FilterExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");

        ExpenseDAO dao = new ExpenseDAO();

        List<Expense> expenses;

        if (category == null || category.equals("") || category.equals("All")) {

            expenses = dao.getAllExpenses();

        } else {

            expenses = dao.getExpensesByCategory(category);
        }

        request.setAttribute("expenseList", expenses);
        request.setAttribute("expenses", expenses);

        RequestDispatcher rd = request.getRequestDispatcher(
                "views/expenses.jsp");

        rd.forward(request, response);
    }
}
