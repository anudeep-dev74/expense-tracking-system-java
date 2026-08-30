package com.expensetracker.test;

import com.expensetracker.entity.OperationHistory;
import com.expensetracker.exception.InvalidOperationHistoryException;
import com.expensetracker.service.OperationHistoryService;
import com.expensetracker.service.impl.OperationHistoryServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class OperationHistoryServiceTest {
    public static void main(String[] args) {
        OperationHistoryService historyService = new OperationHistoryServiceImpl();
        try {
            String historyId1 = historyService.generateHistoryId();
            OperationHistory history1 = new OperationHistory(
                    historyId1,
                    "USR001",
                    "USER",
                    "ADD",
                    "EXPENSE",
                    "EXP001",
                    "Expense added successfully.",
                    LocalDateTime.now()
            );
            historyService.addHistory(history1);
            System.out.println("History 1 added successfully: " + history1.getHistoryId());
            String historyId2 = historyService.generateHistoryId();
            OperationHistory history2 = new OperationHistory(
                    historyId2,
                    "USR001",
                    "USER",
                    "UPDATE",
                    "EXPENSE",
                    "EXP001",
                    "Expense updated successfully.",
                    LocalDateTime.now()
            );
            historyService.addHistory(history2);
            System.out.println("History 2 added successfully: " + historyId2);
            String historyId3 = historyService.generateHistoryId();
            OperationHistory history3 = new OperationHistory(
                    historyId3,
                    "USR002",
                    "USER",
                    "DELETE",
                    "EXPENSE",
                    "EXP003",
                    "Expense deleted successfully.",
                    LocalDateTime.now()
            );
            historyService.addHistory(history3);
            System.out.println("History 3 added successfully: " + historyId3);
            String historyId4 = historyService.generateHistoryId();
            OperationHistory history4 = new OperationHistory(
                    historyId4,
                    "ADM001",
                    "ADMIN",
                    "DEACTIVATE",
                    "USER",
                    "USR002",
                    "User account deactivated successfully.",
                    LocalDateTime.now()
            );
            historyService.addHistory(history4);
            System.out.println("History 4 added successfully: " + historyId4);
            System.out.println("\n========== ALL OPERATION HISTORY ==========");
            List<OperationHistory> allHistory = historyService.getAllHistory();
            if(allHistory.isEmpty()){
                System.out.println("No operation history found");
            }else {
                for (OperationHistory history : allHistory) {
                    System.out.println(history);
                    System.out.println("----------------------------------------");
                }
            }
            System.out.println("\n========== USER USR001 HISTORY ==========");
            List<OperationHistory> user1History = historyService.getHistoryByUserId("USR001");
            if (user1History.isEmpty()) {

                System.out.println("No operation history found for USR001."
                );

            }else {
                for (OperationHistory history : user1History) {
                    System.out.println(history);
                    System.out.println("----------------------------------------");
                }
            }
            System.out.println("\n========== USER USR002 HISTORY ==========");

            List<OperationHistory> user2History = historyService.getHistoryByUserId("USR002");
            if (user2History.isEmpty()) {
                System.out.println("No operation history found for USR002.");
            } else {

                for (OperationHistory history :user2History) {
                    System.out.println(history);
                    System.out.println("----------------------------------------");
                }
            }
            System.out.println(
                    "\n========== ADMIN ADM001 HISTORY =========="
            );

            List<OperationHistory> adminHistory =
                    historyService.getHistoryByUserId(
                            "ADM001"
                    );

            if (adminHistory.isEmpty()) {

                System.out.println(
                        "No operation history found for ADM001."
                );

            } else {

                for (OperationHistory history :
                        adminHistory) {

                    System.out.println(history);

                    System.out.println(
                            "----------------------------------------"
                    );
                }
            }
        }catch (InvalidOperationHistoryException e){
            System.out.println("Unable to process operation history: " + e.getMessage());
        }
    }
}
