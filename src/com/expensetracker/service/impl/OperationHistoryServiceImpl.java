package com.expensetracker.service.impl;

import com.expensetracker.entity.OperationHistory;
import com.expensetracker.exception.InvalidOperationHistoryException;
import com.expensetracker.repository.OperationHistoryRepository;
import com.expensetracker.service.OperationHistoryService;

import java.util.List;

public class OperationHistoryServiceImpl implements OperationHistoryService {

    private OperationHistoryRepository operationHistoryRepository;

    public OperationHistoryServiceImpl(){
        operationHistoryRepository = new OperationHistoryRepository();
    }

    @Override
    public String generateHistoryId() {
        List<OperationHistory> histories = operationHistoryRepository.getAllHistory();
        int nextNumber = 1;
        for (OperationHistory history : histories) {
            String historyId = history.getHistoryId();
            if (historyId == null || !historyId.toUpperCase().startsWith("HIS")) {
                continue;
            }
            try {
                int currentNumber = Integer.parseInt(historyId.substring(3));
                if (currentNumber >= nextNumber) {
                    nextNumber = currentNumber + 1;
                }
            }catch (NumberFormatException e){
                // Ignore invalid history ID format.
            }
        }
        return String.format("HIS%03d", nextNumber);
    }

    /* Add Operation History */
    @Override
    public void addHistory(OperationHistory history) {
        validateHistory(history);
        validateUniqueHistoryId(history.getHistoryId());
        operationHistoryRepository.addHistory(history);
    }

    /* Get All History - Admin */
    @Override
    public List<OperationHistory> getAllHistory() {
        return operationHistoryRepository.getAllHistory();
    }

    /* Get History By User ID - User */
    @Override
    public List<OperationHistory> getHistoryByUserId(String userId) {
        validateUserId(userId);
        return operationHistoryRepository.getHistoryByUserId(userId);
    }

    /* validate Operation History */
    private void validateHistory(OperationHistory history) {
        if (history == null) {
            throw new InvalidOperationHistoryException("Operation history cannot be null.");
        }

        validateHistoryId(history.getHistoryId());
        validateUserId(history.getUserId());

        if(history.getRole() == null || history.getRole().trim().isEmpty()){
            throw new InvalidOperationHistoryException("Operation history role is required.");
        }

        if(history.getOperation() == null || history.getOperation().trim().isEmpty()){
            throw new InvalidOperationHistoryException("Operation type is required.");
        }

        if(history.getRecordType() == null || history.getRecordType().trim().isEmpty()){
            throw new InvalidOperationHistoryException("Record type is required.");
        }

        if(history.getRecordId() == null || history.getRecordId().trim().isEmpty()){
            throw new InvalidOperationHistoryException("Record ID is required.");
        }

        if(history.getDescription() == null || history.getDescription().trim().isEmpty()){
            throw new InvalidOperationHistoryException("Operation description is required.");
        }

        if(history.getOperationDateTime() == null){
            throw new InvalidOperationHistoryException("Operation date and time is required.");
        }
    }

    /* Validate History ID */
    private void validateHistoryId(String historyId) {
        if(historyId == null || historyId.trim().isEmpty()){
            throw new InvalidOperationHistoryException("Please provide a valid history ID.");
        }
    }

    /* Validate User ID */
    private void validateUserId(String userId) {
        if(userId == null || userId.trim().isEmpty()){
            throw new InvalidOperationHistoryException("Please provide a valid user ID.");
        }
    }

    /* Validate Unique History ID */
    private void validateUniqueHistoryId(String historyId) {
        List<OperationHistory> histories = operationHistoryRepository.getAllHistory();
        for(OperationHistory history : histories){
            if(history.getHistoryId().equalsIgnoreCase(historyId)){
                throw new InvalidOperationHistoryException("An operation with history ID '" + historyId + "' already exists.");
            }
        }
    }

}
