package com.expensetracker.menu;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.ReportService;
import com.expensetracker.service.impl.ReportServiceImpl;

import java.util.Map;
import java.util.Scanner;

public class ReportMenu {
    private Scanner input;
    private ReportService reportService;
    private User loggedInUser;

    public ReportMenu(Scanner input, ExpenseService expenseService, User loggedInUser) {
        this.input = input;
        this.reportService = new ReportServiceImpl(expenseService);
        this.loggedInUser = loggedInUser;
    }

    /* Display Report Menu */
    public void showMenu() {
        while (true) {
            boolean isAdmin = "ADMIN".equalsIgnoreCase(loggedInUser.getRole());
            System.out.println("\n========================================");
            System.out.println("             EXPENSE REPORTS");
            System.out.println("========================================");
            if(isAdmin) {
                System.out.println("            SYSTEM REPORTS");
            }else {
                System.out.println("            USER REPORTS");
            }
            System.out.println("========================================");
            System.out.println("1. Total Expense");
            System.out.println("2. Expense Summary By Category");
            System.out.println("3. Expense Summary By Payment Mode");
            /* User - wise reports is available only for Admin */
            if(isAdmin) {
                System.out.println("4. Expense Summary By User");
                System.out.println("5. Monthly Expense Summary");
                System.out.println("6. Highest Expense");
                System.out.println("7. Lowest Expense");
                System.out.println("8. Back");
            }else {
                System.out.println("4. Monthly Expense Summary ");
                System.out.println("5. Back");
            }
            System.out.println("========================================");

            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            if(isAdmin) {
                switch (choice) {
                    case 1:
                        displayTotalExpense();
                        break;
                    case 2:
                        displayExpenseByCategory();
                        break;
                    case 3:
                        displayExpenseByPaymentMode();
                        break;
                    case 4:
                        displayExpenseByUser();
                        break;
                    case 5:
                        displayMonthlyExpenseSummary();
                        break;
                    case 6:
                        displayHighestExpense();
                        break;
                    case 7:
                        displayLowestExpense();
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select an option from 1 to 8");
                }
            }else {
                switch (choice) {
                    case 1:
                        displayTotalExpense();
                        break;
                    case 2:
                        displayExpenseByCategory();
                        break;
                    case 3:
                        displayExpenseByPaymentMode();
                        break;
                    case 4:
                        displayMonthlyExpenseSummary();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select an option from 1 to 5");
                }
            }

        }
    }
    /* Display Total Expenses */
    private void displayTotalExpense() {
        double totalExpense;
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            totalExpense = reportService.getTotalExpense();
        }else {
            totalExpense = reportService.getTotalExpenseByUser(loggedInUser.getUserId());
        }
        System.out.println("\n========== TOTAL EXPENSE ==========");
        System.out.printf("Total Expense: ₹%.2f%n" , totalExpense);
    }
    /* Display Expense By Category */
    private void displayExpenseByCategory() {
        Map<String, Double> categorySummary;
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            categorySummary = reportService.getExpenseSummaryByCategory();
        }else {
            categorySummary = reportService.getExpenseSummaryByCategory(loggedInUser.getUserId());
        }
        System.out.println("\n========== EXPENSE BY CATEGORY ==========");
        if(categorySummary.isEmpty()){
            System.out.println("No expense data is available for reporting.");
            return;
        }
        for(Map.Entry<String, Double> entry : categorySummary.entrySet()){
            System.out.printf("%-20s : ₹%.2f%n",  entry.getKey(), entry.getValue());
        }
    }
    /* Display Expense By Payment Mode */
    private void displayExpenseByPaymentMode() {
        Map<String, Double> paymentModeSummary;
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            paymentModeSummary = reportService.getExpenseSummaryByPaymentMode();
        }else {
            paymentModeSummary = reportService.getExpenseSummaryByPaymentMode(loggedInUser.getUserId());
        }
        System.out.println("\n========== EXPENSE BY PAYMENT MODE ==========");
        if(paymentModeSummary.isEmpty()){
            System.out.println("No expense data is available for reporting.");
            return;
        }
        for(Map.Entry<String, Double> entry : paymentModeSummary.entrySet()){
            System.out.printf("%-20s : ₹%.2f%n", entry.getKey(), entry.getValue());
        }
    }
    /* Display Monthly Expense Summary */
    private void displayMonthlyExpenseSummary() {
        Map<String, Double> monthlySummary;
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            monthlySummary = reportService.getMonthlyExpenseSummary();
        }else {
            monthlySummary = reportService.getMonthlyExpenseSummary(loggedInUser.getUserId());
        }
        System.out.println("\n========== MONTHLY EXPENSE SUMMARY ==========");
        if(monthlySummary.isEmpty()){
            System.out.println("No expense data is available for reporting.");
            return;
        }
        for(Map.Entry<String, Double> entry : monthlySummary.entrySet()){
            System.out.printf("%-20s : ₹%.2f%n", entry.getKey(), entry.getValue());
        }
    }
    /* Display Expense By User - Admin */
    private void displayExpenseByUser() {
        Map<String, Double> userSummary = reportService.getExpenseSummaryByUser();
        System.out.println("\n========== EXPENSE BY USER ==========");
        if(userSummary.isEmpty()){
            System.out.println("No expense data is available for reporting.");
            return;
        }
        for(Map.Entry<String, Double> entry : userSummary.entrySet()){
            System.out.printf("User ID: %-15s : ₹%.2f%n", entry.getKey(), entry.getValue());
        }
    }
    /* Display Highest Expense - Admin */
    private void displayHighestExpense(){
        Expense highestExpense =  reportService.getHighestExpense();
        System.out.println("\n========== HIGHEST EXPENSE ==========");
        if(highestExpense==null) {
            System.out.println("No expense data is available for reporting.");
            return;
        }
        System.out.println("Expense ID     : " + highestExpense.getExpenseId());
        System.out.println("User ID        : " + highestExpense.getUserId());
        System.out.println("Expense Name   : " + highestExpense.getExpenseName());
        System.out.println("Category       : " + highestExpense.getCategory());
        System.out.printf("Amount         : %.2f%n", highestExpense.getAmount());
        System.out.println("Expense Date   : " + highestExpense.getExpenseDate());
        System.out.println("Payment Mode   : " + highestExpense.getPaymentMode());
        System.out.println("Description    : " + highestExpense.getDescription());
    }

    /* Display Lowest Expense */
    private void displayLowestExpense(){
        Expense lowestExpense =  reportService.getLowestExpense();
        System.out.println("\n========== LOWEST EXPENSE =========");
        if(lowestExpense==null) {
            System.out.println("No expense data is available for reporting.");
            return;
        }
        System.out.println("Expense ID     : " + lowestExpense.getExpenseId());
        System.out.println("User ID        : " + lowestExpense.getUserId());
        System.out.println("Expense Name   : " + lowestExpense.getExpenseName());
        System.out.println("Category       : " + lowestExpense.getCategory());
        System.out.printf("Amount         : %.2f%n", lowestExpense.getAmount());
        System.out.println("Expense Date   : " + lowestExpense.getExpenseDate());
        System.out.println("Payment Mode   : " + lowestExpense.getPaymentMode());
        System.out.println("Description    : " + lowestExpense.getDescription());
    }
}
