package com.expense.controller;

import com.expense.dao.UserDAO;
import com.expense.model.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/googleLogin")
public class GoogleLoginServlet extends HttpServlet {

    // Replace with your actual Client ID from Google Cloud Console
    private static final String CLIENT_ID = "145131493824-v5ticedsn6ddqjue97t7ss8nnnn0g3jb.apps.googleusercontent.com";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idTokenString = request.getParameter("idtoken");

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                String email = payload.getEmail();
                String name = (String) payload.get("name");

                UserDAO dao = new UserDAO();
                User user = dao.getUserByEmail(email);

                if (user == null) {
                    // Register new user via Google
                    user = new User();
                    user.setEmail(email);
                    user.setUsername(name);
                    user.setPassword("GOOGLE_AUTH_USER"); // Placeholder
                    dao.registerUser(user);
                }

                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.getWriter().write("success");

            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid ID token.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
