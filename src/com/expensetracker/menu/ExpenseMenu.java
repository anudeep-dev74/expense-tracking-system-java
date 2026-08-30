package com.expensetracker.menu;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.service.ExpenseService;


import java.util.List;
import java.util.Scanner;

public class ExpenseMenu {
    private Scanner input;
    private ExpenseService expenseService;
    private User loggedInUser;

    public ExpenseMenu(User loggedInUser, ExpenseService expenseService) {
        input = new Scanner(System.in);
        this.loggedInUser = loggedInUser;
        this.expenseService = expenseService;
    }

    public void showMenu(){
        while(true){
            System.out.println("\n==============================");
            System.out.println("    EXPENSE TRACKING SYSTEM     ");
            System.out.println("================================");
            System.out.println("1. Add New Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Search Expenses");
            System.out.println("4. Update Expenses");
            System.out.println("5. Delete Expenses");
            System.out.println("6. View Reports");
            System.out.println("7. View Operation History");
            System.out.println("8. Exit");
            System.out.println("=================================");

            System.out.println("Enter the choice: ");
            int choice = input.nextInt();
            input.nextLine();
            switch(choice){
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewAllExpenses();
                    break;
                case 3:
                    searchExpenses();
                    break;
                case 4:
                    updateExpenses();
                    break;
                case 5:
                    deleteExpenses();
                    break;
                case 6:
                    viewReports();
                    break;
                case 7:
                    viewHistory();
                    break;
                case 8:
                    System.out.println("Thank you for using Expense Tracking System");
                    return;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 8");
            }
        }
    }
    /* Add Expense */
    private void addExpense(){
        System.out.println("\n========ADD EXPENSE==========");

        System.out.println("Enter Expense ID: ");
        String expenseId = input.nextLine();

        System.out.println("Enter Expense Name: ");
        String expenseName = input.nextLine();

        System.out.println("Enter the Category : ");
        String category = input.nextLine();

        System.out.println("Enter the Amount : ");
        double amount = input.nextDouble();
        input.nextLine();

        System.out.println("Enter the Expense Date : ");
        String expenseDate = input.nextLine();

        System.out.println("Enter the Payment Mode: ");
        String paymentMode = input.nextLine();

        System.out.println("Enter the Expense Description : ");
        String expenseDescription = input.nextLine();

        Expense expense = new Expense(
                expenseId, loggedInUser.getUserId(), expenseName, category, amount, expenseDate, paymentMode, expenseDescription
        );
        try{
            expenseService.addExpense(expense);
            System.out.println("Expense Added Successfully");
        }catch(InvalidExpenseException e){
            System.out.println("Unable to add expense: " + e.getMessage());

        }
    }

    /* View All Expenses */
    private void viewAllExpenses(){
        System.out.println("\n========VIEW ALL EXPENSES==========");
        List<Expense> expenses = expenseService.getExpensesByUserId(loggedInUser.getUserId());
        if(expenses.isEmpty()){
            System.out.println("No expenses found for your account");
            return;
        }
        for(Expense expense: expenses){
            System.out.println(expense);
            System.out.println("-----------------------");
        }
    }
    /* Search Expense */
    private void searchExpenses(){
        System.out.println("\n========SEARCH EXPENSES==========");
        System.out.println("Enter Expense ID: ");
        String expenseId = input.nextLine();
        try {
            Expense expense = expenseService.getExpenseByIdAndUserId(expenseId,  loggedInUser.getUserId());
            System.out.println("\nExpense Found");
            System.out.println(expense);
        }catch (InvalidExpenseException | ExpenseNotFoundException e){
            System.out.println("Search Failed: " + e.getMessage());
        }
    }
    /* Update Expense */
    private void updateExpenses(){
        System.out.println("\n========UPDATE EXPENSES==========");
        System.out.print("Enter Expense ID to update: ");
        String expenseId = input.nextLine();

        System.out.print("Enter Expense Name: ");
        String expenseName = input.nextLine();

        System.out.print("Enter Category: ");
        String category = input.nextLine();

        System.out.print("Enter Amount: ");
        double amount = input.nextDouble();
        input.nextLine();

        System.out.print("Enter Expense Date: ");
        String expenseDate = input.nextLine();

        System.out.print("Enter Payment Mode: ");
        String paymentMode = input.nextLine();

        System.out.print("Enter Description: ");
        String description = input.nextLine();

        Expense updatedExpense = new Expense(
                expenseId, loggedInUser.getUserId(), expenseName, category, amount, expenseDate, paymentMode, description
        );
        try {
            expenseService.updateExpenseByUser(expenseId, loggedInUser.getUserId(),updatedExpense);
            System.out.println("Expense Updated Successfully");
        }catch (InvalidExpenseException | ExpenseNotFoundException e){
            System.out.println("Unable to update expense: " + e.getMessage());
        }
    }
    /* Delete Expense */
    private void deleteExpenses(){
        System.out.println("\n========DELETE EXPENSES==========");
        System.out.println("Enter Expense ID to delete: ");
        String expenseId = input.nextLine();
        try {
            expenseService.deleteExpenseByUser(expenseId,  loggedInUser.getUserId());
            System.out.println("Expense Deleted Successfully");
        }catch (InvalidExpenseException | ExpenseNotFoundException e){
            System.out.println("Unable to delete expense: " + e.getMessage());
        }
    }

    /* View reports */
    private void viewReports(){
        ReportMenu reportMenu = new ReportMenu(input, expenseService, loggedInUser);
        reportMenu.showMenu();
    }

    /* View Operation History */
    private void viewHistory(){
        HistoryMenu historyMenu = new HistoryMenu(input, loggedInUser);
        historyMenu.showMenu();
    }
}
