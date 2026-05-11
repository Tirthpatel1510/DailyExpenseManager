package com.expense.controller;

import java.io.IOException;

import com.expense.dao.UserDAO;
import com.expense.model.User;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet
        extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic Validation
        String errorMsg = null;
        if (username == null || username.trim().isEmpty()) {
            errorMsg = "Username is required";
        } else if (email == null || email.trim().isEmpty()) {
            errorMsg = "Email is required";
        } else if (password == null || password.length() < 6) {
            errorMsg = "Password must be at least 6 characters long";
        }

        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        com.expense.service.UserService userService = new com.expense.service.UserService();
        
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        
        // Note: UserService.registerUser will hash the password and check for existing email
        // Wait, let's check UserService.registerUser implementation
        
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(hashedPassword);

        UserDAO dao = new UserDAO();
        if (dao.getUserByEmail(email) != null) {
            request.setAttribute("error", "Email already registered!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        if (dao.registerUser(user)) {
            response.sendRedirect("login.jsp?msg=Account created successfully!");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
