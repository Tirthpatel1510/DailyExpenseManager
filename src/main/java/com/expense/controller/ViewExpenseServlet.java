package com.expense.controller;

import com.expense.model.Expense;
import com.expense.model.User;
import com.expense.service.ExpenseService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/viewExpenses")
public class ViewExpenseServlet extends HttpServlet {
    private ExpenseService expenseService = new ExpenseService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            String userEmail = user.getEmail();
            
            // Pagination parameters
            int page = 1;
            int size = 10;
            if (request.getParameter("page") != null) {
                try {
                    page = Integer.parseInt(request.getParameter("page"));
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            // Search query and Date range
            String searchQuery = request.getParameter("query");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");
            
            List<Expense> expenses;
            long totalExpenses;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                expenses = expenseService.searchExpenses(searchQuery, userEmail);
                totalExpenses = expenses.size();
            } else if (fromDateStr != null && !fromDateStr.isEmpty() && toDateStr != null && !toDateStr.isEmpty()) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date startDate = sdf.parse(fromDateStr);
                    java.util.Date endDate = sdf.parse(toDateStr);
                    expenses = new com.expense.dao.ExpenseDAO().getExpensesByDateRange(userEmail, startDate, endDate);
                    totalExpenses = expenses.size();
                } catch (Exception e) {
                    expenses = expenseService.getPaginatedExpenses(userEmail, (page - 1) * size, size);
                    totalExpenses = expenseService.getExpenseCount(userEmail);
                }
            } else {
                int offset = (page - 1) * size;
                expenses = expenseService.getPaginatedExpenses(userEmail, offset, size);
                totalExpenses = expenseService.getExpenseCount(userEmail);
            }

            int totalPages = (int) Math.ceil((double) totalExpenses / size);

            request.setAttribute("expenses", expenses);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("searchQuery", searchQuery);
            request.setAttribute("fromDate", fromDateStr);
            request.setAttribute("toDate", toDateStr);

            // Dashboard statistics
            request.setAttribute("totalCredit", expenseService.getTotalCredit(userEmail));
            request.setAttribute("totalDebit", expenseService.getTotalDebit(userEmail));
            request.setAttribute("balance", expenseService.getTotalCredit(userEmail) - expenseService.getTotalDebit(userEmail));
            request.setAttribute("monthlySavings", expenseService.getMonthlySavings(userEmail));
            
            // Additional data for graphs/filters (simplified for brevity, can be optimized)
            // Note: ExpenseDAO should probably be used through service here too
            com.expense.dao.ExpenseDAO expenseDAO = new com.expense.dao.ExpenseDAO();
            request.setAttribute("categories", expenseDAO.getAllCategories(userEmail));
            request.setAttribute("categoryTotals", expenseService.getCategoryTotals(userEmail));
            request.setAttribute("trends", expenseDAO.getMonthlyExpenseTrends(userEmail));
            request.setAttribute("highestCategory", expenseDAO.getHighestSpendingCategory(userEmail));

            request.getRequestDispatcher("views/expenses.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
