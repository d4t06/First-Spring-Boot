package com.example.demo.system.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.category.exception.CategoryNotFoundException;
import com.example.demo.system.MyResponse;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    MyResponse handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return new MyResponse(false,"bla bla", HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    MyResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach(err -> {
            String key = ((FieldError) (err)).getField();
            String val = err.getDefaultMessage();
            map.put(key, val);
        });

        return new MyResponse(false, "Provided arguments are invalid", HttpStatus.BAD_REQUEST.value(), map);
    }
}