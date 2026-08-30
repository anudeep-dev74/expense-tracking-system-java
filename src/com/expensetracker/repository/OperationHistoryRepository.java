package com.expensetracker.repository;

import com.expensetracker.entity.OperationHistory;
import com.expensetracker.utility.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class OperationHistoryRepository {
    private ArrayList<OperationHistory> histories;

    public OperationHistoryRepository() {
        histories = FileUtility.loadOperationHistories();
    }

    /* Add Histories */
    public void addHistory(OperationHistory history){
        histories.add(history);
        FileUtility.saveOperationHistories(histories);
    }

    /* Get All History */
    public List<OperationHistory> getAllHistory(){
        return histories;
    }

    /* Get History By User ID */
    public List<OperationHistory> getHistoryByUserId(String userId){
        List<OperationHistory> userHistories = new ArrayList<>();
        for(OperationHistory history : histories){
            if(history.getUserId().equalsIgnoreCase(userId)){
                userHistories.add(history);
            }
        }
        return userHistories;
    }

}
