package com.example.assignment.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( //validation failure at dto/entity class throws MethodArgumentNotValidException
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        GenericErrorResponse genericErrorResponse = new GenericErrorResponse()
                .setInstant(Instant.now())
                .setMessage("Oops! something went wrong")
                .setErrors(errors);
        return new ResponseEntity<>(genericErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    protected ResponseEntity<GenericErrorResponse> handleAppException(RuntimeException ex){
//        AppException appException = (AppException) ex;
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        GenericErrorResponse genericErrorResponse = new GenericErrorResponse()
                .setInstant(Instant.now())
                .setMessage("Oops! something went wrong")
                .setErrors(errors);
        return new ResponseEntity<>(genericErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
