package com.expensetracker.test;

import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidUserException;
import com.expensetracker.exception.UserNotFoundException;
import com.expensetracker.service.UserService;
import com.expensetracker.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserServiceTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        UserService userService = new UserServiceImpl();
        /* Register User */
        System.out.println("\n========== USER REGISTRATION ==========");
        System.out.println("Enter User ID: ");
        String userId = input.nextLine();

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.println("Enter Email: ");
        String email = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        System.out.print("Enter Contact Number: ");
        String contactNumber = input.nextLine();

        User user = new User(userId, username, email, password, contactNumber, null, null);

        try {
            userService.registerUser(user);
            System.out.println("User Registered Successfully");
        }catch (InvalidUserException e){
            System.out.println("Registration Failed " + e.getMessage());
        }

        /* View All Users */
        System.out.println("========== REGISTERED USERS ==========");
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            System.out.println("No registered users found.");
        }else {
            for(User registeredUser : users){
                System.out.println(registeredUser);
                System.out.println("----------------------------------------");
            }
        }

        /* User Login */
        System.out.println("========== USER LOGIN ==========");
        System.out.println("Enter Email: ");
        String loginEmail = input.nextLine();

        System.out.println("Enter Password: ");
        String loginPassword = input.nextLine();

        try{
            User loggedInUser = userService.login(loginEmail, loginPassword);
            System.out.println("\n Login Successfully");
            System.out.println("----------------------------------------");
            System.out.println(loggedInUser);
        }catch (InvalidUserException e){
            System.out.println("Login Failed " + e.getMessage());
        }

        /* Get User By ID */
        System.out.println("========== FIND USER BY ID ==========");
        System.out.println("Enter User ID: ");
        String searchUserId = input.nextLine();

        try{
            User foundUser = userService.getUserById(searchUserId);
            System.out.println("\n User Found.");
            System.out.println("----------------------------------------");
            System.out.println(foundUser);
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("User Not Found " + e.getMessage());
        }

        /* Get User by Email */
        System.out.println("========== FIND USER BY EMAIL ==========");
        System.out.println("Enter Email: ");
        String searchEmail = input.nextLine();
        try{
            User foundUser = userService.getUserByEmail(searchEmail);
            System.out.println("\n User Found.");
            System.out.println("----------------------------------------");
            System.out.println(foundUser);
        }catch (InvalidUserException | UserNotFoundException e){
            System.out.println("Search Failed " + e.getMessage());
        }
        input.close();
    }
}
