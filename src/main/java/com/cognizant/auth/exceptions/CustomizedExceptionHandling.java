package com.cognizant.auth.exceptions;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cognizant.auth.dto.APIResponseError;

//import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleExceptions( BadCredentialsException exception, WebRequest webRequest) {
        APIResponseError response = new APIResponseError();
        response.setDateTime(LocalDateTime.now());
        response.setError("Incorrect Password");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.OK);
        return entity;
    }
    
    
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleExceptions( UsernameNotFoundException exception, WebRequest webRequest) {
        APIResponseError response = new APIResponseError();
        response.setDateTime(LocalDateTime.now());
        response.setError(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }
    
    
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleExceptions( SignatureException exception, WebRequest webRequest) {
        APIResponseError response = new APIResponseError();
        response.setDateTime(LocalDateTime.now());
        response.setError(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        return entity;
    }
//    
//    
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<Object> handleExceptions( MalformedJwtException exception, WebRequest webRequest) {
//        APIResponseError response = new APIResponseError();
//        response.setDateTime(LocalDateTime.now());
//        response.setError(exception.getMessage());
//        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//        return entity;
//    }
//    
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<Object> handleExceptions( AuthenticationException exception, WebRequest webRequest) {
//        APIResponseError response = new APIResponseError();
//        response.setDateTime(LocalDateTime.now());
//        response.setError(exception.getMessage());
//        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//        return entity;
//    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions( Exception exception, WebRequest webRequest) {
        APIResponseError response = new APIResponseError();
        response.setDateTime(LocalDateTime.now());
        response.setError(exception.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        return entity;
    }
}