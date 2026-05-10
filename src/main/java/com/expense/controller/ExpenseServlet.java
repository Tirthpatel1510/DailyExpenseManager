package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/saveExpense")
public class ExpenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            Expense expense = new Expense();

            expense.setTitle(request.getParameter("title"));

            expense.setAmount(
                    Double.parseDouble(
                            request.getParameter("amount")));

            expense.setCategory(
                    request.getParameter("category"));

            expense.setExpenseDate(
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(request.getParameter("expenseDate")));

            ExpenseDAO dao = new ExpenseDAO();

            dao.saveExpense(expense);

            response.sendRedirect("views/addExpense.jsp");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
