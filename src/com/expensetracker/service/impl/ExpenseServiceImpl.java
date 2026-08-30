package com.expensetracker.service.impl;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.OperationHistory;
import com.expensetracker.exception.ExpenseNotFoundException;
import com.expensetracker.exception.InvalidExpenseException;
import com.expensetracker.repository.ExpenseRepository;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.OperationHistoryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private ExpenseRepository expenseRepository;
    private OperationHistoryService operationHistoryService;

    public ExpenseServiceImpl(){
        expenseRepository = new ExpenseRepository();
        operationHistoryService = new OperationHistoryServiceImpl();
    }

    /* Add Expense */
    @Override
    public void addExpense(Expense expense) {
        validateExpense(expense);
        validateUniqueExpenseId(expense.getExpenseId());
        expenseRepository.addExpense(expense);
        /* Creates history only after the expense has been successfully saved */
        createUserHistory(expense.getUserId(), "ADD", "EXPENSE", expense.getExpenseId(), "Expense added successfully");
    }

    /* Get All Expenses - Admin*/
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.getExpenses();
    }

    /* Get All Expenses - User */
    @Override
    public List<Expense> getExpensesByUserId(String userId) {
        validateUserId(userId);
        return expenseRepository.getExpensesByUserId(userId);
    }

    /* Get Expense By Id - Admin */
    @Override
    public Expense getExpenseById(String expenseId) {
        validateExpenseId(expenseId);
        Expense expense =  expenseRepository.getExpensesById(expenseId);
        if(expense == null){
            throw new ExpenseNotFoundException("Expense Not Found with ID: " + expenseId);
        }
        return expense;
    }

    /* Get Expense By ID and User ID - User */
    @Override
    public Expense getExpenseByIdAndUserId(String expenseId, String userId) {
        validateExpenseId(expenseId);
        validateUserId(userId);
        Expense expense = expenseRepository.getExpenseByIdAndUserId(expenseId, userId);
        if(expense == null){
            throw new ExpenseNotFoundException("Expense Not Found with ID: " + expenseId);
        }
        return expense;
    }

    /* Update Expense - Admin */
    @Override
    public void updateExpense(String adminId, String expenseId, Expense updatedExpense) {

       validateExpenseId(expenseId);
       validateUserId(adminId);
       validateExpense(updatedExpense);

       Expense existingExpense = expenseRepository.getExpensesById(expenseId);
       if(existingExpense == null){
           throw new ExpenseNotFoundException("Cannot Update. Expense Not Found with ID: " + expenseId);
       }

       updatedExpense.setUserId(existingExpense.getUserId());

       boolean updated = expenseRepository.updateExpenses(expenseId, updatedExpense);
       if(!updated){
           throw new  ExpenseNotFoundException("Unable to update expense with ID: " + expenseId);
       }
       /* Create Admin History */
       createAdminHistory(adminId, "UPDATE", "EXPENSE", expenseId, "Expense updated successfully");
    }

    /* Update Expense - User */
    @Override
    public void updateExpenseByUser(String expenseId, String userId, Expense updatedExpense) {
        validateExpenseId(expenseId);
        validateUserId(userId);
        validateExpense(updatedExpense);
        Expense existingExpense = expenseRepository.getExpenseByIdAndUserId(expenseId, userId);
        if(existingExpense == null){
            throw new ExpenseNotFoundException("Cannot Update.  No Expense Found with ID: " + expenseId);
        }
        updatedExpense.setUserId(userId);
        boolean updated = expenseRepository.updateExpensesByUser(expenseId, userId, updatedExpense);
        if(!updated){
            throw new ExpenseNotFoundException("Unable to Update Expense with ID: " + expenseId);
        }
        /* Create history only after successful update. */
        createUserHistory(userId, "UPDATE", "EXPENSE", expenseId, "Expense updated successfully");
    }

    /* Delete Expense - Admin */
    @Override
    public void deleteExpense(String adminId, String expenseId) {
        validateExpenseId(expenseId);
        validateUserId(adminId);
        Expense existingExpense = expenseRepository.getExpensesById(expenseId);
        if(existingExpense == null){
            throw new ExpenseNotFoundException("Cannot delete. Expense with ID: " + expenseId);
        }
        boolean deleted = expenseRepository.deleteExpenses(expenseId);
        if(!deleted){
            throw new ExpenseNotFoundException("Cannot delete. Expense with ID: " + expenseId);
        }
        /* Create Admin Operation History */
        createAdminHistory(
                adminId,
                "DELETE",
                "EXPENSE",
                expenseId,
                "Expense deleted successfully"
        );
    }

    /* Delete Expense - User */
    @Override
    public void deleteExpenseByUser(String expenseId, String userId) {
        validateExpenseId(expenseId);
        validateUserId(userId);
        Expense existingExpense = expenseRepository.getExpenseByIdAndUserId(expenseId, userId);
        if(existingExpense == null){
            throw new ExpenseNotFoundException("No expense found with ID: " + expenseId);
        }
        boolean deleted = expenseRepository.deleteExpensesByUser(expenseId, userId);
        if(!deleted){
            throw new ExpenseNotFoundException("Unable to delete expense with ID: " + expenseId);
        }
        /* Create  history only after successful deletion */
        createUserHistory(userId, "DELETE", "EXPENSE", expenseId, "Expense deleted successfully");
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

    /* Create User Operation History */
    private void createUserHistory(String userId, String operation, String recordType, String recordId, String description){
        String historyId = operationHistoryService.generateHistoryId();
        OperationHistory history = new OperationHistory(
                historyId,
                userId,
                "USER",
                operation,
                recordType,
                recordId,
                description,
                LocalDateTime.now()
        );
        operationHistoryService.addHistory(history);
    }

    /* Validate the Expense Object */
    private void validateExpense(Expense expense){
        if(expense  == null){
            throw new InvalidExpenseException("Expense cannot be Empty");
        }
        validateExpenseId(expense.getExpenseId());
        validateUserId(expense.getUserId());
        if(expense.getExpenseName() == null || expense.getExpenseName().trim().isEmpty()){
            throw new InvalidExpenseException("Please provide a expense name");
        }
        if(expense.getCategory() == null || expense.getCategory().trim().isEmpty()){
            throw new InvalidExpenseException("Category cannot be null or Empty");
        }
        if(expense.getAmount() <= 0){
            throw new InvalidExpenseException("Expense amount must be greater than zero");
        }
        if(expense.getExpenseDate() == null || expense.getExpenseDate().trim().isEmpty()){
            throw new InvalidExpenseException("Please provide a expense date.");
        }
        try {
            LocalDate.parse(
                    expense.getExpenseDate(),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
            );
        }catch (DateTimeParseException e){
            throw new InvalidExpenseException("Invalid expense date. Please use dd-MM-yyyy format.");
        }
        if(expense.getPaymentMode() ==  null || expense.getPaymentMode().trim().isEmpty()){
            throw new InvalidExpenseException("Please provide a payment mode.");
        }
    }

    /* validate Expense By ID */
    private void validateExpenseId(String expenseId){
        if(expenseId == null || expenseId.trim().isEmpty()){
            throw new InvalidExpenseException("Please provide a valid expense ID.");
        }
    }

    /* validate User ID */
    private void validateUserId(String userId){
        if(userId == null || userId.trim().isEmpty()){
            throw new InvalidExpenseException("Please provide a valid user ID.");
        }
    }


    /* Validate Unique Expense ID */
    private void validateUniqueExpenseId(String expenseId){
        Expense existingExpense = expenseRepository.getExpensesById(expenseId);
        if(existingExpense != null){
            throw new InvalidExpenseException("An expense with ID '" + expenseId +  "' already exists.");
        }
    }
}
