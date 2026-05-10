package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/updateExpense")
public class UpdateExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        String title = request.getParameter("title");

        double amount = Double.parseDouble(
                request.getParameter("amount"));

        String category = request.getParameter("category");

        Expense expense = new Expense();

        expense.setId(id);
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setCategory(category);

        ExpenseDAO dao = new ExpenseDAO();

        dao.updateExpense(expense);

        response.sendRedirect(
                request.getContextPath()
                        + "/viewExpenses");
    }
}
