package org.winside.entities;

public enum ErrorMessage {
    AGE_ERROR_MESSAGE("Incorrect age for a Student");
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }
}
