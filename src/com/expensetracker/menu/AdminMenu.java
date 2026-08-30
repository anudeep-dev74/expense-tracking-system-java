package com.expensetracker.menu;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidUserException;
import com.expensetracker.exception.UserNotFoundException;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private Scanner input;
    private UserService userService;
    private ExpenseService expenseService;
    private User loggedInAdmin;

    public AdminMenu(User loggedInAdmin, UserService userService, ExpenseService expenseService) {
        input = new Scanner(System.in);
        this.userService = userService;
        this.expenseService = expenseService;
        this.loggedInAdmin =  loggedInAdmin;
    }

    /* Display Admin Menu */
    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("             ADMIN DASHBOARD");
            System.out.println("========================================");
            System.out.println("Welcome, " + loggedInAdmin.getUsername());
            System.out.println("----------------------------------------");
            System.out.println("1. View All Users");
            System.out.println("2. Search User By ID");
            System.out.println("3. Search User By Email");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Activate User");
            System.out.println("7. Deactivate User");
            System.out.println("8. View All Expenses");
            System.out.println("9. View Reports");
            System.out.println("10. View Operation History");
            System.out.println("11. Logout");
            System.out.println("========================================");
            System.out.println("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    searchUserByID();
                    break;
                case 3:
                    searchUserByEmail();
                    break;
                case 4:
                    updateUser();
                    break;
                case 5:
                    deleteUser();
                    break;
                case 6:
                    activateUser();
                    break;
                case 7:
                    deactivateUser();
                    break;
                case 8:
                    viewAllExpenses();
                    break;
                case 9:
                    viewReports();
                    break;
                case 10:
                    viewHistory();
                    break;
                case 11:
                    System.out.println("You have logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 11.");
            }
        }
    }
    /* View All Users */
    private void viewAllUsers() {

        System.out.println("\n========== REGISTERED USERS ==========");
        List<User> users = userService.getAllUsers();

        if(users.isEmpty()){
            System.out.println("No registered users were found");
            return;
        }
        for(User user : users){
            System.out.println(user);
            System.out.println("----------------------------------------");
        }
    }
    /* Search User by ID */
    private void searchUserByID() {
        System.out.println("\n========= SEARCH USER BY ID =========");
        System.out.println("Enter User ID: ");
        String userId = input.nextLine();

        try {
            User user = userService.getUserById(userId);
            System.out.println("\nUser Found");
            System.out.println("----------------------------------------");
            System.out.println(user);
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("Search Failed " + e.getMessage());
        }
    }
    /* Search User by Email */
    private void searchUserByEmail() {
        System.out.println("\n========= SEARCH USER BY EMAIL =========");
        System.out.println("Enter Email: ");
        String email = input.nextLine();
        try {
            User user = userService.getUserByEmail(email);
            System.out.println("\nUser Found");
            System.out.println("---------------------------------------");
            System.out.println(user);
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("Search Failed " + e.getMessage());
        }
    }
    /* View All Expenses */
    private void viewAllExpenses() {
        System.out.println("\n========= VIEW ALL EXPENSES =========");
        List<Expense> expenses = expenseService.getAllExpenses();
        if(expenses.isEmpty()){
            System.out.println("No expenses were found");
            return;
        }
        for(Expense expense : expenses){
            System.out.println(expense);
            System.out.println("------------------------------------");
        }
    }

    /* Activate Users */
    private void activateUser(){
        System.out.println("\n========= ACTIVATE USER =========");
        System.out.println("Enter User ID to activate: ");
        String userId = input.nextLine();

        try {
            userService.activateUser(loggedInAdmin.getUserId(), userId);
            System.out.println("User '" + userId + "' has been activated successfully.");
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("Activation Failed " + e.getMessage());
        }
    }

    /* Deactivate User */
    private void deactivateUser(){
        System.out.println("\n========== DEACTIVATE USER ==========");
        System.out.println("Enter User ID to deactivate: ");
        String userId = input.nextLine();

        /* Prevent administrator from deactivating their own account */
        if(loggedInAdmin.getUserId().equalsIgnoreCase(userId)){
            System.out.println("You can not deactivate your administrator account.");
            return;
        }

        try {
            userService.deactivateUser(loggedInAdmin.getUserId(), userId);
            System.out.println("User '" + userId + "' has been deactivated successfully.");
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("Deactivation Failed " + e.getMessage());
        }
    }

    /* Update User */
    private void updateUser() {

        System.out.println("\n========== UPDATE USER ==========");
        System.out.print("Enter User ID to update: ");
        String userId = input.nextLine();
        try {

            User existingUser = userService.getUserById(userId);
            System.out.println("\nCurrent User Details:");
            System.out.println("----------------------------------------");
            System.out.println(existingUser);

            System.out.println("\nEnter New User Details:");

            System.out.print("Enter Username: ");
            String username = input.nextLine();

            System.out.print("Enter Email: ");
            String email = input.nextLine();

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            System.out.print("Enter Contact Number: ");
            String contactNumber = input.nextLine();

            User updatedUser = new User(
                    userId,
                    username,
                    email,
                    password,
                    contactNumber,
                    existingUser.getRole(),
                    existingUser.getStatus()
            );

            userService.updateUser(loggedInAdmin.getUserId(),userId,updatedUser);

            System.out.println("User '" + userId + "' has been updated successfully.");

        } catch (InvalidUserException | UserNotFoundException e) {

            System.out.println("Update Failed: " + e.getMessage());
        }
    }

    /* Delete User */
    private void deleteUser() {

        System.out.println("\n========== DELETE USER ==========");
        System.out.print("Enter User ID to delete: ");
        String userId = input.nextLine();
        if (loggedInAdmin.getUserId().equalsIgnoreCase(userId)) {
            System.out.println("You cannot delete your administrator account.");
            return;
        }

        try {
            User user = userService.getUserById(userId);
            System.out.println("\nUser Found:");
            System.out.println("----------------------------------------");
            System.out.println(user);

            System.out.print("\nAre you sure you want to permanently delete this user? (Y/N): ");

            String confirmation = input.nextLine();

            if (!confirmation.equalsIgnoreCase("Y")) {
                System.out.println("User deletion cancelled.");
                return;
            }
            userService.deleteUser(loggedInAdmin.getUserId(),userId);
            System.out.println("User '" + userId + "' has been deleted successfully.");

        } catch (InvalidUserException | UserNotFoundException e) {

            System.out.println("Deletion Failed: " + e.getMessage()
            );
        }
    }

    /* View Reports */
    private void viewReports(){
        ReportMenu reportMenu = new ReportMenu(input, expenseService, loggedInAdmin);
        reportMenu.showMenu();
    }

    /* View Operation History */
    private void viewHistory() {
        HistoryMenu historyMenu = new HistoryMenu(input, loggedInAdmin);
        historyMenu.showMenu();
    }
}

