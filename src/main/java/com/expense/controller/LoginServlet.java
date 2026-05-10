package com.expense.controller;

import com.expense.dao.UserDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");

        String password = request.getParameter("password");

        UserDAO dao = new UserDAO();

        boolean valid = dao.validateUser(username, password);

        if (valid) {

            HttpSession session = request.getSession();

            session.setAttribute("user", username);

            response.sendRedirect(
                    request.getContextPath()
                            + "/viewExpenses");

        } else {

            response.getWriter()
                    .println("Invalid Username or Password");
        }
    }
}
