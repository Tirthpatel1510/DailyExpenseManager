package com.expense.service;

import com.expense.model.Expense;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseServiceTest {

    @Test
    public void testCalculateTotal() {
        List<Expense> expenses = new ArrayList<>();
        
        Expense e1 = new Expense();
        e1.setAmount(100.0);
        e1.setType("Credit");
        
        Expense e2 = new Expense();
        e2.setAmount(50.0);
        e2.setType("Credit");
        
        expenses.add(e1);
        expenses.add(e2);
        
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        assertEquals(150.0, total);
    }

    @Test
    public void testExpenseCategory() {
        Expense expense = new Expense();
        expense.setCategory("Food");
        assertEquals("Food", expense.getCategory());
    }
}
