package com.example.internintelligence_movieapidevelopment.exception.handler;

import com.example.internintelligence_movieapidevelopment.exception.response.ExceptionDto;
import com.example.internintelligence_movieapidevelopment.exception.*;
import com.example.internintelligence_movieapidevelopment.exception.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handler(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionDto handlerIllegalArgumentException(IllegalArgumentException ex) {
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)//401
    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionDto handleUnauthorizedException(UnauthorizedException ex) {
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)//404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionDto handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ExceptionDto(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)//409
    @ExceptionHandler(AlreadyExistException.class)
    public ExceptionDto handleAlreadyExistException(AlreadyExistException ex) {
        return new ExceptionDto(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ExceptionDto handleGenericException(RuntimeException ex) {
        return new ExceptionDto(ex.getMessage());
    }


}
