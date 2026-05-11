package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/saveExpense")
public class AddExpenseServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            Expense expense = new Expense();
            expense.setTitle(request.getParameter("title"));
            expense.setAmount(Double.parseDouble(request.getParameter("amount")));

            String category = request.getParameter("category");

            if (category.equals("Other")) {
                category = request.getParameter("customCategory");
            }

            expense.setCategory(category);
            expense.setExpenseDate(
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(request.getParameter("expenseDate")));
            expense.setTransactionType(request.getParameter("transactionType"));
            expense.setType(request.getParameter("type"));
            expense.setEmail(user.getEmail());

            ExpenseDAO dao = new ExpenseDAO();
            dao.saveExpense(expense);

            response.sendRedirect("views/addExpense.jsp");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}