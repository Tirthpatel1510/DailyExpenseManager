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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String title = request.getParameter("title");
        String amountStr = request.getParameter("amount");
        String category = request.getParameter("category");
        String dateStr = request.getParameter("expenseDate");
        String type = request.getParameter("type");

        if (category != null && category.equals("Other")) {
            category = request.getParameter("customCategory");
        }

        // Validation
        String errorMsg = null;
        double amount = 0;

        if (title == null || title.trim().isEmpty()) {
            errorMsg = "Title is required";
        } else if (amountStr == null || amountStr.trim().isEmpty()) {
            errorMsg = "Amount is required";
        } else {
            try {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    errorMsg = "Amount must be greater than zero";
                }
            } catch (NumberFormatException e) {
                errorMsg = "Invalid amount format";
            }
        }

        if (errorMsg == null && (dateStr == null || dateStr.trim().isEmpty())) {
            errorMsg = "Date is required";
        }

        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("views/addExpense.jsp").forward(request, response);
            return;
        }

        try {
            Expense expense = new Expense();
            expense.setTitle(title);
            expense.setAmount(amount);
            expense.setCategory(category);
            expense.setExpenseDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
            expense.setType(type);
            expense.setTransactionType(type); // Setting both as in original code
            expense.setEmail(user.getEmail());

            com.expense.service.ExpenseService service = new com.expense.service.ExpenseService();
            service.saveExpense(expense);

            response.sendRedirect(request.getContextPath() + "/viewExpenses?msg=Expense added successfully");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error saving expense: " + e.getMessage());
            request.getRequestDispatcher("views/addExpense.jsp").forward(request, response);
        }
    }
}