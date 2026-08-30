package com.expensetracker.service.impl;

import com.expensetracker.entity.Expense;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.ReportService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportServiceImpl implements ReportService {

    private ExpenseService expenseService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public ReportServiceImpl(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /* ==================== ADMIN REPORTS ==================== */

    /* Calculate Total Expenses */
    @Override
    public double getTotalExpense() {
        double totalExpense = 0.0;
        List<Expense> expenses = expenseService.getAllExpenses();
        for(Expense expense : expenses) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }

    /* Expense Summary by Category */
    @Override
    public Map<String, Double> getExpenseSummaryByCategory() {
        Map<String, Double> categorySummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getAllExpenses();
        for(Expense expense: expenses) {
            String category = expense.getCategory();
            categorySummary.put(
                    category,
                    categorySummary.getOrDefault(category, 0.0) + expense.getAmount()
            );
        }
        return categorySummary;
    }

    /* Expense Summary by Payment Mode */
    @Override
    public Map<String, Double> getExpenseSummaryByPaymentMode() {
        Map<String, Double> paymentModeSummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getAllExpenses();
        for(Expense expense : expenses) {
            String paymentMode = expense.getPaymentMode();
            paymentModeSummary.put(
                    paymentMode,
                    paymentModeSummary.getOrDefault(paymentMode, 0.0) + expense.getAmount()
            );
        }
        return paymentModeSummary;
    }

    /* Monthly Expense Summary */
    @Override
    public Map<String, Double> getMonthlyExpenseSummary() {
        Map<String, Double> monthlySummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getAllExpenses();
        for(Expense expense : expenses) {
            try {
                LocalDate date = LocalDate.parse(expense.getExpenseDate(), DATE_FORMATTER);
                String monthYear = String.format("%02d-%d",  date.getMonthValue(), date.getYear());
                monthlySummary.put(monthYear, monthlySummary.getOrDefault(monthYear, 0.0) + expense.getAmount());
            }catch (DateTimeParseException e) {
                throw new IllegalStateException("Unable to generate monthly report. " +
                        "Invalid expense date found: " + expense.getExpenseDate());
            }
        }
        return monthlySummary;
    }
    /* Highest Expense */
    @Override
    public Expense getHighestExpense() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if(expenses.isEmpty()) {
            return null;
        }
        Expense highestExpense = expenses.get(0);
        for(Expense expense : expenses) {
            if(expense.getAmount() > highestExpense.getAmount()) {
                highestExpense = expense;
            }
        }
        return highestExpense;
    }
    /* Lowest Expense */
    @Override
    public Expense getLowestExpense() {
        List<Expense> expenses = expenseService.getAllExpenses();
        if(expenses.isEmpty()) {
            return null;
        }
        Expense lowestExpense = expenses.get(0);
        for(Expense expense : expenses) {
            if(expense.getAmount() < lowestExpense.getAmount()) {
                lowestExpense = expense;
            }
        }
        return lowestExpense;
    }

    /* Expense Summary by User */
    @Override
    public Map<String, Double> getExpenseSummaryByUser() {
        Map<String, Double> userSummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getAllExpenses();
        for (Expense expense : expenses) {
            String userId = expense.getUserId();
            userSummary.put(
                    userId,
                    userSummary.getOrDefault(userId, 0.0) + expense.getAmount()
            );
        }
        return userSummary;
    }
    /* ==================== USER REPORTS ==================== */
    /* Calculate Total Expenses - User */
    @Override
    public double getTotalExpenseByUser(String userId) {
        double totalExpense = 0.0;
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        for(Expense expense : expenses) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }
    /* Expense Summary By Category - User */
    @Override
    public Map<String, Double> getExpenseSummaryByCategory(String userId) {
        Map<String, Double> categorySummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        for(Expense expense : expenses) {
            String category = expense.getCategory();
            categorySummary.put(
                    category,
                    categorySummary.getOrDefault(category, 0.0) + expense.getAmount()
            );
        }
        return categorySummary;
    }
    /* Expense Summary By Payment Mode */
    @Override
    public Map<String, Double> getExpenseSummaryByPaymentMode(String userId) {
        Map<String, Double> paymentModeSummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        for(Expense expense : expenses) {
            String paymentMode = expense.getPaymentMode();
            paymentModeSummary.put(
                    paymentMode,
                    paymentModeSummary.getOrDefault(paymentMode, 0.0) + expense.getAmount()
            );
        }
        return paymentModeSummary;
    }
    /* Monthly Expense Summary  */
    @Override
    public Map<String, Double> getMonthlyExpenseSummary(String userId) {
        Map<String, Double> monthlySummary = new LinkedHashMap<>();
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        for(Expense expense : expenses) {
            addExpenseToMonthlySummary(monthlySummary, expense);
        }
        return monthlySummary;
    }
    /* Add Expense Amount to Monthly Summary */
    private void addExpenseToMonthlySummary(Map<String, Double> monthlySummary, Expense expense) {
        try {
            LocalDate date = LocalDate.parse(expense.getExpenseDate(), DATE_FORMATTER);
            String monthYear = String.format("%02d-%d",  date.getMonthValue(), date.getYear());
            monthlySummary.put(
                    monthYear, monthlySummary.getOrDefault(monthYear, 0.0) + expense.getAmount()
            );
        }catch (DateTimeParseException e) {
            throw new  IllegalStateException(
                    "Unable to generate monthly report. " + "Invalid expense date found: " + expense.getExpenseDate()
            );
        }
    }
}
