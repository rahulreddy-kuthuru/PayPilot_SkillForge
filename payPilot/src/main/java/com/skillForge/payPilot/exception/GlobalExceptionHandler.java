package com.skillForge.payPilot.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skillForge.payPilot.dto.ApiError;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ApiError error = new ApiError(LocalDateTime.now(),
                                      HttpStatus.BAD_REQUEST.value(),
                                      "Invalid JSON",
                                      "Malformed request body or unexpected field names",
                                      request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiError> handleBindException(BindException ex, HttpServletRequest request) {
        ApiError error = new ApiError(LocalDateTime.now(),
                                      HttpStatus.BAD_REQUEST.value(),
                                      "Validation Error",
                                      ex.getMessage(),
                                      request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("An unexpected error occurred: " + ex.getMessage());
    }
    
    @ExceptionHandler(MerchantNotFoundException.class)
    public ResponseEntity<ApiError> handleMerchantNotFound(MerchantNotFoundException ex, HttpServletRequest request) {
        ApiError error = new ApiError(LocalDateTime.now(),
                                      HttpStatus.NOT_FOUND.value(),
                                      "Merchant Not Found",
                                      ex.getMessage(),
                                      request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidKycTransitionException.class)
    public ResponseEntity<ApiError> handleInvalidKycTransition(InvalidKycTransitionException ex, HttpServletRequest request) {
        ApiError error = new ApiError(LocalDateTime.now(),
                                      HttpStatus.CONFLICT.value(),
                                      "Invalid KYC Transition",
                                      ex.getMessage(),
                                      request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }



}
