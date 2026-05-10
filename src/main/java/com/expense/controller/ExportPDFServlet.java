package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/exportPDF")
public class ExportPDFServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");

        response.setHeader("Content-Disposition",
                "attachment; filename=expenses.pdf");

        try {

            Document document = new Document();

            PdfWriter.getInstance(
                    document,
                    response.getOutputStream());

            document.open();

            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    20);

            Paragraph title = new Paragraph(
                    "Daily Expense Report",
                    titleFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);

            table.setWidthPercentage(100);

            table.addCell("ID");
            table.addCell("Title");
            table.addCell("Amount");
            table.addCell("Category");
            table.addCell("Date");

            ExpenseDAO dao = new ExpenseDAO();

            List<Expense> expenses = dao.getAllExpenses();

            for (Expense e : expenses) {

                table.addCell(String.valueOf(e.getId()));
                table.addCell(e.getTitle());
                table.addCell(String.valueOf(e.getAmount()));
                table.addCell(e.getCategory());
                table.addCell(String.valueOf(e.getExpenseDate()));
            }

            document.add(table);

            document.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
