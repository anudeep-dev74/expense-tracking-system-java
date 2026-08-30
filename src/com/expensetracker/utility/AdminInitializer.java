package com.expensetracker.utility;

import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidUserException;
import com.expensetracker.service.UserService;
import com.expensetracker.service.impl.UserServiceImpl;

import java.util.List;

public class AdminInitializer {
    private UserService userService;
    public AdminInitializer(){
        userService = new UserServiceImpl();
    }
    public void initializeAdmin(){
        List<User> users = userService.getAllUsers();
        for(User user: users){
            if("ADMIN".equalsIgnoreCase(user.getRole())){
                return;
            }
        }
        User admin = new User("ADM001", "System Administrator", "admin@expensetracker.com", "Admin@2026", "9390663049", "ADMIN", "ACTIVE");
        try {
            userService.registerUser(admin);
            System.out.println("System Administrator account initialized successfully");
        }catch (InvalidUserException e){
            System.out.println("Unable to initialize the administrator account.");
        }
    }

}
