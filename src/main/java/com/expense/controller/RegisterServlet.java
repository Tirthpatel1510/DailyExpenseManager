package com.expense.controller;

import java.io.IOException;

import com.expense.dao.UserDAO;
import com.expense.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet
        extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");

        String email = request.getParameter("email");

        String password = request.getParameter("password");

        User user = new User();

        user.setUsername(username);

        user.setEmail(email);

        user.setPassword(password);

        UserDAO dao = new UserDAO();

        dao.registerUser(user);

        response.sendRedirect(
                "views/login.jsp");
    }
}
