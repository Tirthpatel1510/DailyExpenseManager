package com.expense.service;

import com.expense.dao.UserDAO;
import com.expense.model.User;

/**
 * Service layer for User related business logic.
 */
public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String email, String password) {
        return userDAO.login(email, password);
    }

    public boolean registerUser(User user) {
        // Here we could add additional business logic like checking if email exists
        if (userDAO.getUserByEmail(user.getEmail()) != null) {
            return false;
        }
        return userDAO.registerUser(user);
    }
}
