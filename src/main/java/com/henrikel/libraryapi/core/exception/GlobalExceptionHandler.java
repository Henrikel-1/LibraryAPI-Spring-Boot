package com.henrikel.libraryapi.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException manvex){
        Map<String, String> errors = new HashMap<>();
        manvex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)  error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(Map.of("errors", errors));
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessValidation(BusinessException ex){
        HttpStatus status = switch (ex.getTipo()) {
            case RECURSO_NAO_ENCONTRADO -> HttpStatus.NOT_FOUND;
            case CONFLITO -> HttpStatus.CONFLICT;
            case REGRA_NEGOCIO -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        return ResponseEntity.status(status).body(Map.of("erro", ex.getMessage()));
    }
}
