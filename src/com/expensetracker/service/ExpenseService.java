package com.expensetracker.service;

import com.expensetracker.entity.Expense;

import java.util.List;

public interface ExpenseService {
    /* Add Expense */
    void addExpense(Expense expense);
    /* Get All Expenses - Admin */
    List<Expense> getAllExpenses();
    /* Get Expenses By User ID - User */
    List<Expense> getExpensesByUserId(String userId);
    /* Get Expense By Id - Admin */
    Expense getExpenseById(String expenseId);
    /* Get Expense By ID and User ID - User */
    Expense getExpenseByIdAndUserId(String expenseId, String userId);
    /* Update Expense - Admin */
    void updateExpense(String adminId, String expenseId, Expense updatedExpense);
    /* Update Expense - User */
    void updateExpenseByUser(String expenseId, String userId, Expense updatedExpense);
    /* Delete Expense - Admin */
    void  deleteExpense(String adminId, String expenseId);
    /* Delete Expense - User */
    void deleteExpenseByUser(String expenseId, String userId);
}
