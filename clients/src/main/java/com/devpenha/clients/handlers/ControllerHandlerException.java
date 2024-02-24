package com.devpenha.clients.handlers;

import com.devpenha.clients.dto.CustomError;
import com.devpenha.clients.dto.ValidationError;
import com.devpenha.clients.exceptions.DatabaseException;
import com.devpenha.clients.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerHandlerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> entittyNotFound(ResourceNotFoundException e, HttpServletRequest request){
        var status = HttpStatus.NOT_FOUND.value();

        var error = new CustomError(Instant.now(),status,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(error);

    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseNotFound(DatabaseException e, HttpServletRequest request){
        var status = HttpStatus.BAD_REQUEST.value();
        var error = new CustomError(Instant.now(),status,e.getMessage(),request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request){
        var status = HttpStatus.UNPROCESSABLE_ENTITY.value();
        var err = new ValidationError(Instant.now(),status,"Dados Inválidos",request.getRequestURI());
        for(FieldError f: e.getBindingResult().getFieldErrors()){
            err.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(err);
    }
}
