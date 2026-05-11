package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/exportCSV")
public class ExportCSVServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        com.expense.model.User user = (com.expense.model.User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=expenses_" + user.getEmail() + ".csv");

        ExpenseDAO dao = new ExpenseDAO();
        List<Expense> expenses;

        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");

        try {
            if (fromDateStr != null && !fromDateStr.isEmpty() && toDateStr != null && !toDateStr.isEmpty()) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date startDate = sdf.parse(fromDateStr);
                java.util.Date endDate = sdf.parse(toDateStr);
                expenses = dao.getExpensesByDateRange(user.getEmail(), startDate, endDate);
            } else {
                expenses = dao.getAllExpenses(user.getEmail());
            }

            PrintWriter writer = response.getWriter();
            CSVPrinter csvPrinter = new CSVPrinter(writer,
                    CSVFormat.DEFAULT.withHeader("ID", "Title", "Amount", "Category", "Type", "Date"));

            for (Expense e : expenses) {
                csvPrinter.printRecord(
                        e.getId(), e.getTitle(), e.getAmount(), e.getCategory(), e.getType(), e.getExpenseDate());
            }
            csvPrinter.flush();
            csvPrinter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
