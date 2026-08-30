package com.expensetracker.service;

import com.expensetracker.entity.OperationHistory;

import java.util.List;

public interface OperationHistoryService {

    /* Generate Next History ID */
    String generateHistoryId();

    /* Add Operation History */
    void addHistory(OperationHistory history);

    /* Get All Operation History - Admin */
    List<OperationHistory> getAllHistory();

    /* Get Operation History By User ID - User */
    List<OperationHistory> getHistoryByUserId(String userId);
}
