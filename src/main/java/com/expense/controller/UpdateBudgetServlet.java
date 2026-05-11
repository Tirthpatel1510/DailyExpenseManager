package com.expense.controller;

import com.expense.dao.UserDAO;
import com.expense.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/updateBudget")
public class UpdateBudgetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            try {
                double budget = Double.parseDouble(request.getParameter("budget"));
                user.setBudget(budget);

                UserDAO dao = new UserDAO();
                // I need to add an update method to UserDAO or use Hibernate directly
                // Let's check if UserDAO has update. It doesn't seem to based on previous view.
                
                // For now, let's assume we add an update method to UserDAO
                dao.updateUser(user);
                
                session.setAttribute("user", user);
                response.sendRedirect("viewExpenses?msg=Budget updated successfully");
            } catch (NumberFormatException e) {
                response.sendRedirect("viewExpenses?error=Invalid budget amount");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
