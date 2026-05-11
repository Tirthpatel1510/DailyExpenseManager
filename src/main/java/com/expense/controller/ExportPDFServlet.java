package com.expense.controller;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

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

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=expenses_" + user.getEmail() + ".pdf");

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Add Header
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Daily Expense Manager - Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
            Paragraph subTitle = new Paragraph("Generated for: " + user.getEmail() + "\nDate: " + new java.util.Date().toString(), subTitleFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            
            document.add(new Paragraph(" ")); // Spacer
            document.add(new Paragraph(" "));

            // Add Table
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Header Style
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            PdfPCell cell;

            String[] headers = {"ID", "Title", "Amount", "Category", "Type", "Date"};
            for (String header : headers) {
                cell = new PdfPCell(new Phrase(header, headFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.BLACK);
                table.addCell(cell);
            }

            ExpenseDAO dao = new ExpenseDAO();
            List<Expense> expenses;

            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            if (fromDateStr != null && !fromDateStr.isEmpty() && toDateStr != null && !toDateStr.isEmpty()) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date startDate = sdf.parse(fromDateStr);
                java.util.Date endDate = sdf.parse(toDateStr);
                expenses = dao.getExpensesByDateRange(user.getEmail(), startDate, endDate);
                
                Paragraph filterInfo = new Paragraph("Report Period: " + fromDateStr + " to " + toDateStr, subTitleFont);
                filterInfo.setAlignment(Element.ALIGN_CENTER);
                document.add(filterInfo);
            } else {
                expenses = dao.getAllExpenses(user.getEmail());
            }

            if (expenses == null || expenses.isEmpty()) {
                Paragraph noData = new Paragraph("\nNo expenses found for this period.", FontFactory.getFont(FontFactory.HELVETICA, 14, BaseColor.RED));
                noData.setAlignment(Element.ALIGN_CENTER);
                document.add(noData);
            } else {
                double totalAmount = 0;
                for (Expense e : expenses) {
                    table.addCell(String.valueOf(e.getId()));
                    table.addCell(e.getTitle());
                    table.addCell("Rs. " + e.getAmount());
                    table.addCell(e.getCategory());
                    table.addCell(e.getType());
                    table.addCell(String.valueOf(e.getExpenseDate()));
                    totalAmount += e.getAmount();
                }
                
                // Add Summary Row
                PdfPCell totalCellLabel = new PdfPCell(new Phrase("TOTAL AMOUNT", headFont));
                totalCellLabel.setColspan(2);
                totalCellLabel.setBackgroundColor(BaseColor.DARK_GRAY);
                table.addCell(totalCellLabel);
                
                PdfPCell totalCellValue = new PdfPCell(new Phrase("Rs. " + String.format("%.2f", totalAmount), headFont));
                totalCellValue.setColspan(4);
                totalCellValue.setBackgroundColor(BaseColor.DARK_GRAY);
                table.addCell(totalCellValue);

                document.add(table);
            }
            
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
