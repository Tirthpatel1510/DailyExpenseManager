package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/updateExpense")
public class UpdateExpenseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        com.expense.model.User user = (com.expense.model.User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        String title = request.getParameter("title");
        String amountStr = request.getParameter("amount");
        String category = request.getParameter("category");
        String expenseDateStr = request.getParameter("expenseDate");
        String type = request.getParameter("type");

        // Validation
        String errorMsg = null;
        int id = 0;
        double amount = 0;
        Date expenseDate = null;

        try {
            id = Integer.parseInt(idStr);
            if (title == null || title.trim().isEmpty()) {
                errorMsg = "Title is required";
            } else if (amountStr == null || amountStr.trim().isEmpty()) {
                errorMsg = "Amount is required";
            } else {
                amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    errorMsg = "Amount must be greater than zero";
                }
            }
            
            if (errorMsg == null) {
                expenseDate = new SimpleDateFormat("yyyy-MM-dd").parse(expenseDateStr);
            }
        } catch (Exception e) {
            errorMsg = "Invalid input data: " + e.getMessage();
        }

        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("editExpense?id=" + idStr).forward(request, response);
            return;
        }

        Expense expense = new Expense();
        expense.setId(id);
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setExpenseDate(expenseDate);
        expense.setType(type);
        expense.setTransactionType(type);
        expense.setEmail(user.getEmail()); // Security: Ensure email is linked to current user

        com.expense.service.ExpenseService service = new com.expense.service.ExpenseService();
        service.updateExpense(expense);

        response.sendRedirect(request.getContextPath() + "/viewExpenses?msg=Expense updated successfully");
    }
}
