package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/editExpense")
public class EditExpenseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(
                request.getParameter("id"));

        ExpenseDAO dao = new ExpenseDAO();

        Expense expense = dao.getExpenseById(id);

        request.setAttribute("expense", expense);

        RequestDispatcher rd = request.getRequestDispatcher(
                "/views/editExpense.jsp");

        rd.forward(request, response);
    }
}
