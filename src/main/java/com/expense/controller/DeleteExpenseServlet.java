package com.expense.controller;

import com.expense.dao.ExpenseDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/deleteExpense")
public class DeleteExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        ExpenseDAO dao = new ExpenseDAO();

        dao.deleteExpense(id);

        response.sendRedirect("viewExpenses");
    }
}
