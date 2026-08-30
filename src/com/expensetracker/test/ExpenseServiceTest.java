package com.expensetracker.test;

import com.expensetracker.entity.Expense;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.impl.ExpenseServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ExpenseServiceTest {
    static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ExpenseService expenseService = new ExpenseServiceImpl();

        String userId = "USR001";

        System.out.println("Enter then Expense ID : ");
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

        /* Add Expense */
        Expense expense = new Expense(
                expenseId, userId, expenseName,category,amount,expenseDate, paymentMode, expenseDescription
        );
        try {
            expenseService.addExpense(expense);
            System.out.println("Expense added successfully");
        }catch (InvalidExpenseException e){
            System.out.println("Unable to add expense " + e.getMessage());
        }

        /* Get Expenses By User */
        List<Expense> expenses = expenseService.getExpensesByUserId(userId);
        System.out.println("\n--------- My Expenses-----------");
        for(Expense exp: expenses){
            System.out.println(exp);
            System.out.println("-------------------------");
        }
        /* Get Expense By Id */
        try {
            Expense foundExpense = expenseService.getExpenseByIdAndUserId(expenseId, userId);
            System.out.println("\n------Expense Found------");
            System.out.println(foundExpense);
        }catch (InvalidExpenseException |  ExpenseNotFoundException e){
            System.out.println("Search failed: " + e.getMessage());
        }
        input.close();
    }
}
