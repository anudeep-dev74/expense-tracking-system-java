package com.expensetracker.utility;

import com.expensetracker.entity.Expense;
import com.expensetracker.entity.OperationHistory;

import java.io.*;
import java.util.ArrayList;

public class FileUtility {
    private static final String FILE_NAME = "expense.dat";
    private static final String HISTORY_FILE_NAME = "history.dat";

    public static void saveExpenses(ArrayList<Expense> expenses) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            outputStream.writeObject(expenses);
        }catch(IOException e) {
            System.out.println("Error while saving expenses!" + e.getMessage());
        }
    }

    public static ArrayList<Expense> loadExpenses() {
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            return new ArrayList<>();
        }
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            return (ArrayList<Expense>) inputStream.readObject();
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading expenses!" + e.getMessage());
        }
        return new ArrayList<>();
    }
    public static void saveOperationHistories(ArrayList<OperationHistory> histories) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(HISTORY_FILE_NAME))){
            outputStream.writeObject(histories);
        }catch(IOException e) {
            System.out.println("Error while saving operation history! " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public static ArrayList<OperationHistory> loadOperationHistories() {
        File file = new File(HISTORY_FILE_NAME);
        if(!file.exists()) {
            return new ArrayList<>();
        }
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(HISTORY_FILE_NAME))){
            return (ArrayList<OperationHistory>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading operation history! " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
