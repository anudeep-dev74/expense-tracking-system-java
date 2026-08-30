package com.expensetracker.service.impl;

import com.expensetracker.entity.OperationHistory;
import com.expensetracker.entity.User;
import com.expensetracker.exception.InvalidUserException;
import com.expensetracker.exception.UserNotFoundException;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.service.OperationHistoryService;
import com.expensetracker.service.UserService;
import com.expensetracker.utility.PasswordUtility;

import java.time.LocalDateTime;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private OperationHistoryService  operationHistoryService;

    public UserServiceImpl() {
        userRepository = new UserRepository();
        operationHistoryService = new OperationHistoryServiceImpl();
    }

    /* register user */
    @Override
    public void registerUser(User user) {
        validateUser(user);
        User existingUserById = userRepository.getUserById(user.getUserId());
        if(existingUserById != null){
            throw new InvalidUserException("User ID: " + user.getUserId() +" is already registered");
        }

        User existingUserByEmail = userRepository.getUserByEmail(user.getEmail());
        if(existingUserByEmail != null){
            throw new InvalidUserException("An Account is already registered with email: " +  user.getEmail() + ".");
        }

        String passwordHash = PasswordUtility.hashPassword(user.getPasswordHash());
        user.setPasswordHash(passwordHash);

        if(user.getRole() == null || user.getRole().trim().isEmpty()){
            user.setRole("USER");
        }

        if(user.getStatus() == null || user.getStatus().trim().isEmpty()){
            user.setStatus("ACTIVE");
        }
        userRepository.addUser(user);
        /* Create User Registration History */
        createUserHistory(
                user.getUserId(),
                user.getRole(),
                "REGISTER",
                "USER",
                user.getUserId(),
                "User account registered successfully"
        );
    }

    /* Login User */
    @Override
    public User login(String email, String password) {
        validateLoginInput(email, password);
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            throw new InvalidUserException("Invalid email or password");
        }
        if(!"ACTIVE".equalsIgnoreCase(user.getStatus())){
            throw new InvalidUserException("Your account is currently inactive. " + "Please contact administrator.");
        }
        String enteredPasswordHash = PasswordUtility.hashPassword(password);
        if(!enteredPasswordHash.equals(user.getPasswordHash())){
            throw new InvalidUserException("Invalid email or password");
        }
        /* Create User Login History */
        createUserHistory(
                user.getUserId(),
                user.getRole(),
                "LOGIN",
                "USER",
                user.getUserId(),
                "User logged in successfully."
        );
        return user;
    }

    /* Get All Users */
    @Override
    public List<User> getAllUsers(){
        return userRepository.getUsers();
    }

    /* Get User By Id */
    @Override
    public User getUserById(String userId){
        validateUserId(userId);
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("No user was found with ID: " + userId);
        }
        return user;
    }

    /* Get User By Email */
    @Override
    public User getUserByEmail(String email){
        validateEmail(email);
        User user = userRepository.getUserByEmail(email);
        if(user == null){
            throw new UserNotFoundException("No user was found with email: " + email);
        }
        return user;
    }

    /* Update User */
    @Override
    public void updateUser(String adminId, String userId, User updatedUser){
        validateUserId(userId);
        validateAdmin(adminId);
        validateUser(updatedUser);
        User existingUser = userRepository.getUserById(userId);
        if(existingUser == null){
            throw new UserNotFoundException("No user was found with ID: " + userId);
        }

        if(!existingUser.getEmail().equalsIgnoreCase(updatedUser.getEmail())){
            User emailOwner = userRepository.getUserByEmail(updatedUser.getEmail());
            if(emailOwner != null && !emailOwner.getUserId().equals(userId)){
                throw new InvalidUserException("An account is already registered with email " +  updatedUser.getEmail() + ".");
            }
        }

        String newPasswordHash = PasswordUtility.hashPassword(updatedUser.getPasswordHash());
        updatedUser.setPasswordHash(newPasswordHash);
        updatedUser.setUserId(existingUser.getUserId());
        updatedUser.setRole(existingUser.getRole());
        updatedUser.setStatus(existingUser.getStatus());
        boolean updated = userRepository.updateUser(userId, updatedUser);
        if(!updated){
            throw new UserNotFoundException("Unable to update user with ID: " + userId);
        }
        /* Create User Update History */
        createAdminHistory(
                adminId,
                "UPDATE",
                "USER",
                userId,
                "User account updated successfully"
        );
    }

    @Override
    public void activateUser(String adminId, String userId) {
        validateUserId(userId);
        validateAdmin(adminId);
        User user = userRepository.getUserById(userId);
        if(user == null){
            throw new UserNotFoundException("No user was found with ID: " + userId);
        }

        if("ACTIVE".equalsIgnoreCase(user.getStatus())){
            throw new InvalidUserException("User account '" + userId + "' is already active.");
        }

        user.setStatus("ACTIVE");
        boolean updated = userRepository.updateUser(userId, user);

        if(!updated){
            throw new UserNotFoundException("Unable to activate user with ID: " + userId);
        }

        /* Create Admin Operation History */
        createAdminHistory(
                adminId,
                "ACTIVATE",
                "USER",
                userId,
                "User account activated successfully."
        );
    }

    @Override
    public void deactivateUser(String adminId,String userId) {
        validateUserId(userId);
        validateAdmin(adminId);
        User user = userRepository.getUserById(userId);

        if(user == null){
            throw new UserNotFoundException("No user was found with ID: " + userId);
        }

        if("INACTIVE".equalsIgnoreCase(user.getStatus())){
            throw new InvalidUserException("User account '" + userId + "' is already inactive.");
        }
        user.setStatus("INACTIVE");
        boolean updated = userRepository.updateUser(userId, user);
        if(!updated){
            throw new UserNotFoundException("Unable to deactivate user with ID: " + userId);
        }
        /* Create Admin Operation History */
        createAdminHistory(
                adminId,
                "DEACTIVATE",
                "USER",
                userId,
                "User account deactivated successfully."
        );
    }

    /* Delete User */
    @Override
    public void deleteUser(String adminId, String userId){
        validateUserId(userId);
        validateAdmin(adminId);
        User existingUser = userRepository.getUserById(userId);
        if(existingUser == null){
            throw new UserNotFoundException("No user was found with ID: " + userId);
        }
        if("ADMIN".equalsIgnoreCase(existingUser.getRole())){
            throw new InvalidUserException("Administrator accounts cannot be deleted.");
        }
        boolean deleted = userRepository.deleteUser(userId);
        if(!deleted){
            throw new UserNotFoundException("Unable to delete user with ID: " + userId);
        }
        /* Create Admin Deletion History */
        createAdminHistory(
                adminId,
                "DELETE",
                "USER",
                userId,
                "User account deleted successfully."
        );
    }

    /* Create User Operation History */
    private void createUserHistory(String userId, String role, String operation, String recordType, String recordId, String description) {
        String historyId = operationHistoryService.generateHistoryId();
        OperationHistory history = new OperationHistory(
                historyId,
                userId,
                role,
                operation,
                recordType,
                recordId,
                description,
                LocalDateTime.now()
        );
        operationHistoryService.addHistory(history);
    }

    /* Create Admin Operation History */
    private void createAdminHistory(
            String adminId,
            String operation,
            String recordType,
            String recordId,
            String description
    ){
        String historyId = operationHistoryService.generateHistoryId();
        OperationHistory history = new OperationHistory(
                historyId,
                adminId,
                "ADMIN",
                operation,
                recordType,
                recordId,
                description,
                LocalDateTime.now()
        );
        operationHistoryService.addHistory(history);

    }

    private void validateAdmin(String adminId){
        validateUserId(adminId);
        User admin = userRepository.getUserById(adminId);
        if(admin == null){
            throw new UserNotFoundException("Administrator account was not found: " + adminId);
        }
        if(!"ADMIN".equalsIgnoreCase(admin.getRole())){
            throw new InvalidUserException("Only an administrator can perform this operation.");
        }
        if(!"ACTIVE".equalsIgnoreCase(admin.getStatus())){
            throw new InvalidUserException("Administrator account is inactive.");
        }
    }

   /* Validate User Object */
    private void validateUser(User user) {
        if(user == null){
            throw new InvalidUserException("User information is required.");
        }

       validateUserId(user.getUserId());

       validateUsername(user.getUsername());

       validateEmail(user.getEmail());

        if(user.getPasswordHash() == null || user.getPasswordHash().trim().isEmpty()){
            throw new InvalidUserException("Please provide a valid password.");
        }

        if(user.getContactNumber() == null || user.getContactNumber().trim().isEmpty()){
            throw new InvalidUserException("Please provide a valid contact number.");
        }
        if(!user.getContactNumber().matches("\\d{10}")){
            throw new InvalidUserException("Contact number must contain exactly 10 digits");
        }
    }

    /*  validate User ID */
    private void validateUserId(String userId) {
        if(userId == null || userId.trim().isEmpty()){
            throw new InvalidUserException("Please provide a valid user id.");
        }
    }
    /* Validate Username */
    private void validateUsername(String username){
        if(username == null || username.trim().isEmpty()){
            throw new InvalidUserException("Please provide a username.");
        }
    }
    /* Validate Email */
    private void validateEmail(String email){
        if(email == null || email.trim().isEmpty()){
            throw new InvalidUserException("Please provide an Email address.");
        }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw new InvalidUserException("Please provide a valid Email address.");
        }
    }
    /* Validate Login Input */
    private void validateLoginInput(String email, String password) {
        validateEmail(email);
        if(password == null || password.trim().isEmpty()){
            throw new InvalidUserException("Please enter your password.");
        }
    }
}
