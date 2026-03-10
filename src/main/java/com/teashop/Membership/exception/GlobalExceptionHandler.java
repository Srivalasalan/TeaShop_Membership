package com.teashop.Membership.exception;


import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.teashop.Membership.util.ResponseStructure;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleMemberNotFoundException(
            MemberNotFoundException ex) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.NOT_FOUND.value());
        rs.setMessage(ex.getMessage());
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ResponseStructure<String>> handleSubscriptionNotFoundException(
            SubscriptionNotFoundException ex) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.NOT_FOUND.value());
        rs.setMessage(ex.getMessage());
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(rs);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStructure<String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed!");

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage(errorMessage);
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseStructure<String>> handleRuntimeException(
            RuntimeException ex) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage(ex.getMessage());
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleException(
            Exception ex) {

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        rs.setMessage("Something went wrong: " + ex.getMessage());
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rs);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseStructure<String>> handleConstraintViolationException(
            ConstraintViolationException ex) {

        String errorMessage = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .findFirst()
                .orElse("Validation failed!");

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
        rs.setMessage(errorMessage);
        rs.setData(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rs);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResourceFound(NoResourceFoundException ex, 
            HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}