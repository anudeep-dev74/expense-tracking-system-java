package com.expensetracker.menu;

import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidUserException;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import com.expensetracker.service.impl.ExpenseServiceImpl;
import com.expensetracker.service.impl.UserServiceImpl;

import java.util.Scanner;

public class AuthenticationMenu {
    private Scanner input;
    private UserService userService;
    private ExpenseService expenseService;

    public AuthenticationMenu() {
        input = new Scanner(System.in);
        userService = new UserServiceImpl();
        expenseService = new ExpenseServiceImpl();
    }

    /* Display Authentication Menu */
    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("         EXPENSE TRACKING SYSTEM");
            System.out.println("========================================");
            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.println("========================================");

            System.out.println("Enter your choice : ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Thank you for using Expense Tracking System");
                    return;
            }
        }
    }
    /* User Registration */
    private void registerUser() {
        System.out.println("\n========== USER REGISTRATION ==========");

        System.out.println("Enter user ID: ");
        String userID = input.nextLine();

        System.out.println("Enter Username: ");
        String userName = input.nextLine();

        System.out.println("Enter Email: ");
        String email = input.nextLine();

        System.out.println("Enter Password: ");
        String password = input.nextLine();

        System.out.println("Enter Contact Number: ");
        String contactNumber = input.nextLine();

        User user = new User(
                userID, userName, email, password, contactNumber, null, null
        );

        try {
            userService.registerUser(user);
            System.out.println("\n Registration completed Successfully");
            System.out.println("You can now login using registered email address.");
        }catch (InvalidUserException e){
            System.out.println("\nRegistration Failed: " + e.getMessage());
        }
    }

    /* User Login */
    private void loginUser() {
        System.out.println("\n========== USER LOGIN ==========");

        System.out.println("Enter Email: ");
        String email = input.nextLine();

        System.out.println("Enter Password: ");
        String password = input.nextLine();

        try {
            User loggedInUser = userService.login(email, password);
            System.out.println("\nLogin successful. Welcome, " + loggedInUser.getUsername() + "!");
            System.out.println("Role: " + loggedInUser.getRole());
            handleRoleBasedAccess(loggedInUser);
        }catch (InvalidUserException e){
            System.out.println("\nLogin Failed: " + e.getMessage());
        }
    }
    /* Role Based Access */
    private void handleRoleBasedAccess(User loggedInUser) {
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())){
            AdminMenu adminMenu = new AdminMenu(loggedInUser, userService, expenseService);
            adminMenu.showMenu();
        }else if("USER".equalsIgnoreCase(loggedInUser.getRole())){
            ExpenseMenu expenseMenu = new ExpenseMenu(loggedInUser, expenseService);
            expenseMenu.showMenu();
        }else {
            System.out.println("Your account has an unsupported role. " +
                    "Please contact the administrator.");
        }
    }
}
