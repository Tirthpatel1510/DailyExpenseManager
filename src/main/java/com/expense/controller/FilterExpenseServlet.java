package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        ExpenseDAO expenseDAO = new ExpenseDAO();

        List<Expense> expenses;

        if (category == null || category.equals("") || category.equals("All")) {

            expenses = expenseDAO.getAllExpenses(user.getEmail());

        } else {

            expenses = expenseDAO.filterExpenses(category, user.getEmail());
        }

        List<String> categories = expenseDAO.getAllCategories(user.getEmail());

        Map<String, Double> categoryTotals = expenseDAO.getCategoryTotals(user.getEmail());

        request.setAttribute("expenseList", expenses);
        request.setAttribute("expenses", expenses);
        request.setAttribute("selectedCategory", category);
        request.setAttribute("categories", categories);
        request.setAttribute("categoryTotals", categoryTotals);

        double totalCredit = expenseDAO.getTotalCredit(user.getEmail());
        double totalDebit = expenseDAO.getTotalDebit(user.getEmail());

        double balance = totalCredit - totalDebit;

        double monthlySavings = expenseDAO.getMonthlySavings(user.getEmail());

        request.setAttribute("totalCredit", totalCredit);
        request.setAttribute("totalDebit", totalDebit);
        request.setAttribute("balance", balance);
        request.setAttribute("monthlySavings", monthlySavings);

        Map<String, Double> categoryData = expenseDAO.getCategoryTotals(user.getEmail());
        request.setAttribute("categoryData", categoryData);

        RequestDispatcher rd = request.getRequestDispatcher(
                "views/expenses.jsp");

        rd.forward(request, response);
    }
}
