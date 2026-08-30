package com.expensetracker.test;

import com.expensetracker.entity.User;
import com.expensetracker.service.UserService;
import com.expensetracker.service.impl.UserServiceImpl;
import com.expensetracker.utility.AdminInitializer;

import java.util.List;

public class AdminInitializerTest {
    static void main(String[] args) {
        AdminInitializer adminInitializer = new AdminInitializer();
        adminInitializer.initializeAdmin();
        UserService userService = new UserServiceImpl();
        List<User> users = userService.getAllUsers();
        System.out.println("\n========== SYSTEM ADMIN ==========");
        for (User user : users) {
            if("ADMIN".equalsIgnoreCase(user.getRole())) {
                System.out.println("User ID : " + user.getUserId());
                System.out.println("Name    : " + user.getUsername());
                System.out.println("Email   : " + user.getEmail());
                System.out.println("Role    : " + user.getRole());
                System.out.println("Status  : " + user.getStatus());
                break;
            }
        }
    }
}
