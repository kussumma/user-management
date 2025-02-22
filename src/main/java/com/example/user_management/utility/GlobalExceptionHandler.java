package com.example.user_management.utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

/**
 * GlobalExceptionHandler
 * Handles exceptions thrown by the application
 * It can handle ResponseStatusException and HttpClientErrorException
 * to return the correct status code and message
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(
        GlobalExceptionHandler.class
    );

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {

        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(
        HttpStatus status,
        String message,
        WebRequest request
    ) {
        String path =
            ((ServletWebRequest) request).getRequest().getRequestURI();

        logger.error("{}: {}", status.getReasonPhrase(), message);

        ErrorResponse errorResponse = new ErrorResponse(
            LocalDateTime.now().toString(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

    // Handles ResponseStatusException correctly
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(
        ResponseStatusException ex,
        WebRequest request
    ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return createErrorResponse(status, ex.getReason(), request);
    }

    // Handles all HttpClientErrorException cases
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrors(
        HttpClientErrorException ex,
        WebRequest request
    ) {
        return createErrorResponse(
            (HttpStatus) ex.getStatusCode(),
            ex.getMessage(),
            request
        );
    }

    // Prevents unnecessary 500 errors for unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(
        Exception ex,
        WebRequest request
    ) {
        return createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage(),
            request
        );
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
        MethodArgumentNotValidException ex,
        WebRequest request
    ) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors
            .stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));

        return createErrorResponse(
            HttpStatus.BAD_REQUEST,
            errorMessage,
            request
        );
    }
}
