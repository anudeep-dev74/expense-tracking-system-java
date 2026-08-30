package com.expensetracker.repository;

import com.expensetracker.entity.User;

import java.io.*;
import java.util.ArrayList;

public class UserRepository {
    private static final String FILENAME = "users.dat";
    private ArrayList<User> users;

    /* Constructor */
    public UserRepository() {
        users = loadUsers();
    }

    /* Add Users */
    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    /* Get all users */
    public ArrayList<User> getUsers() {
        return users;
    }

    /* get Users By Id */
    public User getUserById(String userId){
        for(User user: users){
            if(user.getUserId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    /* Get User by Email */
    public User getUserByEmail(String email){
        for(User user: users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }

    /* Update User */
    public boolean updateUser(String userId, User updatedUser){
        User existingUser = getUserById(userId);
        if(existingUser != null){
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPasswordHash(updatedUser.getPasswordHash());
            existingUser.setContactNumber(updatedUser.getContactNumber());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setStatus(updatedUser.getStatus());
            saveUsers();
            return true;
        }
        return false;
    }

    /* Delete User */
    public boolean deleteUser(String userId) {
        User existingUser = getUserById(userId);
        if(existingUser != null){
            users.remove(existingUser);
            saveUsers();
            return true;
        }
        return false;
    }

    /*Save Users */
    private void saveUsers(){
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            outputStream.writeObject(users);
        }catch (IOException e){
            System.out.println("Unable to save user information. Please try again.");
        }
    }

    /* Load Users */
    @SuppressWarnings("unchecked")
    private ArrayList<User> loadUsers(){
        File file = new File(FILENAME);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILENAME))){
            return (ArrayList<User>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Unable to load user information.");
        }
        return new ArrayList<>();
    }

}
