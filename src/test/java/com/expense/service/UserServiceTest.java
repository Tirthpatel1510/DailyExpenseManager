package com.expense.service;

import com.expense.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    public void testUserBudget() {
        User user = new User();
        user.setBudget(5000.0);
        assertEquals(5000.0, user.getBudget());
    }

    @Test
    public void testUserEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }
}
