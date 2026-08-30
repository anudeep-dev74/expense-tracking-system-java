package com.expensetracker.test;

import com.expensetracker.entity.Expense;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.ReportService;
import com.expensetracker.service.impl.ExpenseServiceImpl;
import com.expensetracker.service.impl.ReportServiceImpl;

import java.util.Map;

public class ReportServiceTest {
    public static void main(String[] args) {
        ExpenseService expenseService = new ExpenseServiceImpl();
        ReportService reportService = new ReportServiceImpl(expenseService);

        /* Add Test Expenses */
        Expense expense1 = new Expense(
                "EXP201",
                "USR001",
                "Grocery",
                "Food",
                2500.0,
                "23-08-2026",
                "UPI",
                "Monthly groceries"
        );
        Expense expense2 = new Expense(
                "EXP202",
                "USR001",
                "Bus Pass",
                "Travel",
                800.0,
                "24-08-2026",
                "Cash",
                "Monthly travel"
        );
        Expense expense3 = new Expense(
                "EXP203",
                "USR002",
                "Restaurant",
                "Food",
                1200.0,
                "24-08-2026",
                "UPI",
                "Dinner"
        );
        Expense expense4 = new Expense(
                "EXP204",
                "USR002",
                "Clothes",
                "Shopping",
                2000.0,
                "05-09-2026",
                "Card",
                "New clothes"
        );
        try{
            expenseService.addExpense(expense1);
            expenseService.addExpense(expense2);
            expenseService.addExpense(expense3);
            expenseService.addExpense(expense4);
            System.out.println("Test expenses added successfully");
        }catch (InvalidExpenseException e){
            System.out.println("Unable to add test expense: " + e.getMessage());
        }

        /* ==================== ADMIN REPORTS ==================== */
        /* Total Expense */
        System.out.println("\n========== TOTAL EXPENSE ==========");
        double totalExpense = reportService.getTotalExpense();
        System.out.println("Total expenses: ₹" + totalExpense);

        /* Expense Summary By Category */
        System.out.println("\n========== ADMIN EXPENSE BY CATEGORY ==========");
        Map<String, Double> categorySummary = reportService.getExpenseSummaryByCategory();
        for(Map.Entry<String, Double> entry : categorySummary.entrySet()){
            System.out.printf("%-20s : ₹%.2f%n", entry.getKey(), entry.getValue());
        }

        /* Expense Summary By Payment Mode */
        System.out.println("\n========== ADMIN EXPENSE BY PAYMENT MODE==========");
        Map<String, Double> paymentModeSummary = reportService.getExpenseSummaryByPaymentMode();
        for(Map.Entry<String, Double> entry : paymentModeSummary.entrySet()){
            System.out.printf("%-20s : ₹%.2f%n", entry.getKey(), entry.getValue());
        }
        /* Monthly Expense Summary */
        System.out.println("\n========== ADMIN MONTHLY EXPENSE SUMMARY ==========");
        Map<String, Double> monthlySummary = reportService.getMonthlyExpenseSummary();
        for(Map.Entry<String, Double> entry : monthlySummary.entrySet()){
            System.out.println(entry.getKey() + ": ₹" + entry.getValue());
        }

        /* ==================== USER 1 REPORTS ==================== */
        System.out.println("\n========== USER USR001 Reports ==========");
        double user1Total = reportService.getTotalExpenseByUser("USR001");
        System.out.printf("USR001 Total Expense : ₹%.2f%n",  user1Total);
        System.out.println("\n--- USR001 Category Summary ---");

        Map<String, Double> user1CategorySummary = reportService.getExpenseSummaryByCategory("USR001");

        for (Map.Entry<String, Double> entry : user1CategorySummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("\n--- USR001 Payment Mode Summary ---");

        Map<String, Double> user1PaymentSummary =
                reportService.getExpenseSummaryByPaymentMode("USR001");

        for (Map.Entry<String, Double> entry :
                user1PaymentSummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }
        System.out.println("\n--- USR001 Monthly Summary ---");

        Map<String, Double> user1MonthlySummary =
                reportService.getMonthlyExpenseSummary("USR001");

        for (Map.Entry<String, Double> entry :
                user1MonthlySummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        /* ==================== USER 2 REPORTS ==================== */

        System.out.println(
                "\n========== USER USR002 REPORTS =========="
        );

        double user2Total =
                reportService.getTotalExpenseByUser("USR002");

        System.out.printf(
                "USR002 Total Expense : ₹%.2f%n",
                user2Total
        );
        System.out.println("\n--- USR002 Category Summary ---");

        Map<String, Double> user2CategorySummary =
                reportService.getExpenseSummaryByCategory("USR002");

        for (Map.Entry<String, Double> entry :
                user2CategorySummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("\n--- USR002 Payment Mode Summary ---");
        Map<String, Double> user2PaymentSummary =
                reportService.getExpenseSummaryByPaymentMode("USR002");

        for (Map.Entry<String, Double> entry :
                user2PaymentSummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("\n--- USR002 Monthly Summary ---");
        Map<String, Double> user2MonthlySummary =
                reportService.getMonthlyExpenseSummary("USR002");

        for (Map.Entry<String, Double> entry :
                user2MonthlySummary.entrySet()) {

            System.out.printf(
                    "%-20s : ₹%.2f%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }
    }
}
