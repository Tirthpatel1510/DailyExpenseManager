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

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition",
                "attachment; filename=expenses.csv");

        ExpenseDAO dao = new ExpenseDAO();

        List<Expense> expenses = dao.getAllExpenses();

        PrintWriter writer = response.getWriter();

        CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.withHeader(
                        "ID",
                        "Title",
                        "Amount",
                        "Category",
                        "Date"));

        for (Expense e : expenses) {

            csvPrinter.printRecord(
                    e.getId(),
                    e.getTitle(),
                    e.getAmount(),
                    e.getCategory(),
                    e.getExpenseDate());
        }

        csvPrinter.flush();
    }
}
