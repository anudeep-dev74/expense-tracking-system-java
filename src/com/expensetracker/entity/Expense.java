package com.expensetracker.entity;

import java.io.Serializable;

public class Expense implements Serializable {

    /*  version Identifier for Serializable Class */
    private static final long serialVersionUID = 1L;

    private String expenseId;
    private String userId;
    private String expenseName;
    private String category;
    private double amount;
    private String expenseDate;
    private String paymentMode;
    private String description;

    /* Create a Default Constructor */
    public Expense(){

    }
    /* Create Parameterised Constructor */
    public Expense(String expenseId, String userId, String expenseName, String category, double amount, String expenseDate, String paymentMode, String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.paymentMode = paymentMode;
        this.description = description;
    }

    /* Generate Getters and Setters */
    public String getExpenseId() {
        return expenseId;
    }
    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }
    public void setUserId(String userId) {this.userId =  userId;}
    public String getUserId() {return userId;}
    public String getExpenseName() {
        return expenseName;
    }
    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getExpenseDate() {
        return expenseDate;
    }
    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
    public String getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /* Override the toString() method */
    @Override
    public String toString(){
        return "Expense ID    : " + expenseId + "\n" +
                "User ID       : " + userId + "\n" +
                "Expense Name  : " + expenseName + "\n" +
                "Category      : " + category + "\n" +
                "Amount        : " + amount + "\n" +
                "Expense Date  : " + expenseDate + "\n" +
                "Payment Mode  : " + paymentMode + "\n" +
                "Description   : " + description;
    }

}
