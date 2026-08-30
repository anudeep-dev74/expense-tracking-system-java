package com.expensetracker.menu;

import com.expensetracker.entity.OperationHistory;
import com.expensetracker.entity.User;
import com.expensetracker.service.OperationHistoryService;
import com.expensetracker.service.impl.OperationHistoryServiceImpl;

import java.util.List;
import java.util.Scanner;

public class HistoryMenu {
    private Scanner input;
    private OperationHistoryService operationHistoryService;
    private User loggedInUser;

    public HistoryMenu(Scanner input, User loggedInUser) {
        this.input = input;
        this.loggedInUser = loggedInUser;
        this.operationHistoryService = new OperationHistoryServiceImpl();
    }
    /* Display History Menu */
    public void showMenu() {
        while (true) {
            System.out.println("\n========================================");
            System.out.println("            OPERATION HISTORY             ");
            System.out.println("==========================================");
            if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())){
                System.out.println("           SYSTEM HISTORY             ");
            }else {
                System.out.println("           USER HISTORY              ");
            }
            System.out.println("------------------------------------------");
            System.out.println("1. View Operation History");
            System.out.println("2. Back");
            System.out.println("------------------------------------------");
            System.out.println("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    viewHistory();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid choice. Please select an option from 1 to 2");
            }
        }
    }
    /* View Operation History */
    private void viewHistory() {
        List<OperationHistory> histories;
        if("ADMIN".equalsIgnoreCase(loggedInUser.getRole())){
            histories = operationHistoryService.getAllHistory();
        }else {
            histories = operationHistoryService.getHistoryByUserId(loggedInUser.getUserId());
        }
        System.out.println("\n========== OPERATION HISTORY ==========");
        if(histories.isEmpty()){
            System.out.println("No operation history found");
            return;
        }
        for(OperationHistory history : histories){
            System.out.println(history);
            System.out.println("----------------------------------------");
        }
    }
}
