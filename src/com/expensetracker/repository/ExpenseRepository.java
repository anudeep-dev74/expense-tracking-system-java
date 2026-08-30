package com.expensetracker.repository;

import com.expensetracker.entity.Expense;
import com.expensetracker.utility.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository {
    /* Use a generic collection  */
    private ArrayList<Expense> expenses;

    /* Constructor */
    public ExpenseRepository() {
        expenses = FileUtility.loadExpenses();
    }

    /* Add Expense */
    public void addExpense(Expense expense) {
        expenses.add(expense);
        FileUtility.saveExpenses(expenses);
    }

    /* Get All Expenses - Admin Use */
    public ArrayList<Expense> getExpenses() { return expenses;}

    /* Get Expenses By User ID - User use */
    public List<Expense> getExpensesByUserId(String userId) {
        List<Expense> userExpenses = new ArrayList<>();
        for(Expense expense: expenses) {
            if(expense.getUserId().equalsIgnoreCase(userId)) {
                userExpenses.add(expense);
            }
        }
        return userExpenses;
    }

    /*  Get Expense By ID - Admin use */
    public Expense getExpensesById(String expenseId) {
        for(Expense expense: expenses){
            if(expense.getExpenseId().equalsIgnoreCase(expenseId)){
                return expense;
            }
        }
        return null;
    }

    /* Get Expense By ID and User ID - User use */
    public Expense getExpenseByIdAndUserId(String expenseId, String userId) {
        for(Expense expense: expenses){
            if(expense.getExpenseId().equalsIgnoreCase(expenseId) && expense.getUserId().equalsIgnoreCase(userId)){
                return expense;
            }
        }
        return null;
    }

    /* Update the expense  - Admin Use */
    public boolean updateExpenses(String expenseId, Expense updatedExpense) {
        Expense existingExpense = getExpensesById(expenseId);

        if(existingExpense != null){
            existingExpense.setUserId(updatedExpense.getUserId());
            existingExpense.setExpenseName(updatedExpense.getExpenseName());
            existingExpense.setCategory(updatedExpense.getCategory());
            existingExpense.setAmount(updatedExpense.getAmount());
            existingExpense.setExpenseDate(updatedExpense.getExpenseDate());
            existingExpense.setPaymentMode(updatedExpense.getPaymentMode());
            existingExpense.setDescription(updatedExpense.getDescription());

            FileUtility.saveExpenses(expenses);
            return true;
        }
        return false;
    }

    /* Update Expense By User - User use */
    public boolean updateExpensesByUser(String expenseId, String userId, Expense updatedExpense) {
        Expense existingExpense = getExpenseByIdAndUserId(expenseId, userId);
        if(existingExpense != null) {
            existingExpense.setExpenseName(updatedExpense.getExpenseName());
            existingExpense.setCategory(updatedExpense.getCategory());
            existingExpense.setAmount(updatedExpense.getAmount());
            existingExpense.setExpenseDate(updatedExpense.getExpenseDate());
            existingExpense.setPaymentMode(updatedExpense.getPaymentMode());
            existingExpense.setDescription(updatedExpense.getDescription());
            FileUtility.saveExpenses(expenses);
            return true;
        }
        return false;
    }

    /* Delete Expense - Admin Use */
    public boolean deleteExpenses(String expenseId) {
        Expense existingExpense = getExpensesById(expenseId);
        if(existingExpense != null){
            expenses.remove(existingExpense);
            FileUtility.saveExpenses(expenses);
            return true;
        }
        return false;
    }

    /* Delete Expense - User use */
    public boolean deleteExpensesByUser(String expenseId, String userId) {
        Expense existingExpense = getExpenseByIdAndUserId(expenseId, userId);
        if(existingExpense != null){
            expenses.remove(existingExpense);
            FileUtility.saveExpenses(expenses);
            return true;
        }
        return false;
    }
}
