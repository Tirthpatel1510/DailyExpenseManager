package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import jakarta.servlet.http.HttpSession;
import com.expense.model.User;
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

            String category = request.getParameter("category");

            if (category.equals("Other")) {

                category = request.getParameter("customCategory");
            }

            expense.setCategory(category);

            expense.setExpenseDate(
                    new SimpleDateFormat("yyyy-MM-dd")
                            .parse(request.getParameter("expenseDate")));

            expense.setTransactionType(
                    request.getParameter(
                            "transactionType"));

            String type = request.getParameter("type");
            expense.setType(type);

            HttpSession session = request.getSession(false);
            if (session != null) {
                Object userObj = session.getAttribute("user");
                if (userObj != null && userObj instanceof com.expense.model.User) {
                    com.expense.model.User user = (com.expense.model.User) userObj;
                    expense.setUserEmail(user.getEmail());
                }
            }

            ExpenseDAO dao = new ExpenseDAO();

            dao.saveExpense(expense);

            response.sendRedirect("views/addExpense.jsp");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
