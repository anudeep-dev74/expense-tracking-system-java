package com.expensetracker.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String email;
    private String passwordHash;
    private String contactNumber;
    private String role;
    private String status;

    /* Default Constructor */
    public User() {

    }

    /* Parameterized Constructor */
    public User(String userId, String username, String email, String passwordHash, String contactNumber, String role, String status) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.contactNumber = contactNumber;
        this.role = role;
        this.status = status;
    }

    /* Getters and Setters */

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  "User ID        : " + userId + "\n" +
                "Username       : " + username + "\n" +
                "Email          : " + email + "\n" +
                "Contact Number : " + contactNumber + "\n" +
                "Role           : " + role + "\n" +
                "Status         : " + status;
    }
}
