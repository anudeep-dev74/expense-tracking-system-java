package com.expensetracker.service;

import com.expensetracker.entity.Expense;

import java.util.Map;

public interface ReportService {
    /* calculate the total expense */
    double getTotalExpense();

    /*  Get Expense Summary by category */
    Map<String, Double> getExpenseSummaryByCategory();

    /* Get Expense Summary by Payment Mode */
    Map<String, Double> getExpenseSummaryByPaymentMode();

    /* Get Expense Summary By User */
    Map<String, Double> getExpenseSummaryByUser();

    /* Get Monthly Expense Summary */
    Map<String, Double> getMonthlyExpenseSummary();

    /* Get Highest Expense */
    Expense getHighestExpense();

    /* Get Lowest Expense */
    Expense getLowestExpense();

    /* User - specific report */
    double getTotalExpenseByUser(String userId);

    /* User - specific category report */
    Map<String, Double> getExpenseSummaryByCategory(String userId);

    /* User - specific payment mode report */
    Map<String, Double> getExpenseSummaryByPaymentMode(String userId);

    /* User - specific monthly report */
    Map<String, Double> getMonthlyExpenseSummary(String userId);
}
