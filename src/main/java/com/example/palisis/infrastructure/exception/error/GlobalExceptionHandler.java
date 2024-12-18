package com.example.palisis.infrastructure.exception.error;

import com.example.palisis.infrastructure.exception.CustomIllegalArgumentException;
import com.example.palisis.infrastructure.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String RESOURCE_NOT_FOUND_MSG = "The requested resource was not found.";
    private static final String BAD_REQUEST_MSG = "Invalid arguments provided.";
    private static final String VALIDATION_FAILED_MSG = "Validation failed for fields.";
    private static final String REQUEST_BODY_INVALID_MSG = "Request body is missing or invalid.";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An unexpected error occurred.";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Resource not found: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND_MSG, ex.getMessage());
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, BAD_REQUEST_MSG, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, REQUEST_BODY_INVALID_MSG, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> validationErrors = extractValidationErrors(ex);
        log.warn("Validation failed: {}", String.join(", ", validationErrors));
        return buildErrorResponse(HttpStatus.BAD_REQUEST, VALIDATION_FAILED_MSG, validationErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: ", ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG, ex.getMessage());
    }

    private List<String> extractValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .toList();
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, String detailedMessage) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message, detailedMessage);
        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message, List<String> fieldErrors) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message, fieldErrors);
        return new ResponseEntity<>(errorResponse, status);
    }
}
