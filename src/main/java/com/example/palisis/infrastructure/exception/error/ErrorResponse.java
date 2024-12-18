package com.example.palisis.infrastructure.exception.error;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {

    private final int statusCode;
    private final String message;
    private final String detailedMessage;
    private final List<String> fieldErrors;

    public ErrorResponse(int statusCode, String message, String detailedMessage) {
        this.statusCode = statusCode;
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.fieldErrors = null;
    }

    public ErrorResponse(int statusCode, String message, List<String> fieldErrors) {
        this.statusCode = statusCode;
        this.message = message;
        this.detailedMessage = null;
        this.fieldErrors = fieldErrors;
    }

}
