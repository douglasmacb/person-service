package com.criticalsoftware.personservice.enums;

public enum ErrorType {
    PERROR_001("Resource already exists", "perror_01"),
    PERROR_002("Resource not founded", "perror_02");

    public final String label;
    public final String message;

    private ErrorType(String message, String label) {
        this.label = label;
        this.message = message;
    }

    public String getLabel() {
        return this.label;
    }

    public String getMessage() {
        return message;
    }
}
