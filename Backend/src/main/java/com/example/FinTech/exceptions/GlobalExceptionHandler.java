package com.example.FinTech.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleDateFormatError(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException) {
            return ResponseEntity.badRequest().body("❌ Ungültiges Datumsformat – bitte yyyy-MM-dd verwenden.");
        }
        return ResponseEntity.badRequest().body("❌ Ungültige Eingabe.");
    }

    @ExceptionHandler(NotFoundException.class)
public ResponseEntity<Map<String, String>> handleSymbolNotFound(NotFoundException ex) {
    return ResponseEntity.badRequest().body(Map.of("symbol", ex.getMessage()));
}
}
