package com.expensetracker.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String historyId;
    private String userId;
    private String role;
    private String operation;
    private String recordType;
    private String recordId;
    private String description;
    private LocalDateTime operationDateTime;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /* Default Constructor */
    public OperationHistory(){

    }

    /* Parameterized Constructor */
    public OperationHistory(
            String historyId,
            String userId,
            String role,
            String operation,
            String recordType,
            String recordId,
            String description,
            LocalDateTime operationDateTime) {
        this.historyId = historyId;
        this.userId = userId;
        this.role = role;
        this.operation = operation;
        this.recordType = recordType;
        this.recordId = recordId;
        this.description = description;
        this.operationDateTime = operationDateTime;
    }

    /* Getters and Setters */

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getOperationDateTime() {
        return operationDateTime;
    }

    public void setOperationDateTime(LocalDateTime operationDateTime) {
        this.operationDateTime = operationDateTime;
    }

    @Override
    public String toString() {
        String formattedDateTime = operationDateTime != null ? operationDateTime.format(DATE_TIME_FORMATTER) : "N/A";
        return  "History ID       : " + historyId + "\n" +
                "User ID          : " + userId + "\n" +
                "Role             : " + role + "\n" +
                "Operation        : " + operation + "\n" +
                "Record Type      : " + recordType + "\n" +
                "Record ID        : " + recordId + "\n" +
                "Description      : " + description + "\n" +
                "Date & Time      : " + formattedDateTime;
    }
}
