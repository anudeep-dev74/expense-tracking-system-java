package com.expensetracker.exception;

public class InvalidOperationHistoryException extends RuntimeException {
    public InvalidOperationHistoryException(String message) {
        super(message);
    }
}
