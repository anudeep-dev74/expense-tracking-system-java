package com.expensetracker.service;

import com.expensetracker.entity.User;

import java.util.List;

public interface UserService {
    /* Register a new User */
    void registerUser(User user);

    /* Authenticate user during login */
    User login(String email, String password);

    /* Get All Users */
    List<User> getAllUsers();

    /* get user by id */
    User getUserById(String userId);

    /* Get user by name */
    User getUserByEmail(String email);

    /* Update User */
    void updateUser(String adminId, String userId, User updatedUser);

    /* Activate User */
    void activateUser(String adminId, String userId);

    /* Deactivate User */
    void deactivateUser(String adminId, String userId);

    /* Delete User */
    void deleteUser(String adminId, String userId);

}
