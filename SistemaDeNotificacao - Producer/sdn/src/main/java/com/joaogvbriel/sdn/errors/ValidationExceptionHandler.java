package com.joaogvbriel.sdn.errors;

import com.joaogvbriel.sdn.dto.NotificationsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErros = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->{
            fieldErros.put(error.getField(),error.getDefaultMessage());
        });

        response.put("status","error");
        response.put("message","Dados de entrada inválidos");
        response.put("errors", fieldErros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
