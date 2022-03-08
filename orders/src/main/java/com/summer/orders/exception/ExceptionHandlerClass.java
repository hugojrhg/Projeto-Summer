package com.summer.orders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ExceptionHandlerClass {

    private StandardError init(Exception e,String msg , HttpStatus status,HttpServletRequest request){
        var err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError(msg);
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return err;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<StandardError> orderNotFound(OrderNotFoundException e , HttpServletRequest request){
        var err = init(e,"OrderNotFound",HttpStatus.NOT_FOUND,request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<StandardError> orderNotFound(ProductNotFoundException e , HttpServletRequest request){
        var err = init(e,"ProductNotFound",HttpStatus.NOT_FOUND,request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> orderNotFound(UserNotFoundException e , HttpServletRequest request){
        var err = init(e,"UserNotFound",HttpStatus.NOT_FOUND,request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<StandardError> orderNotFound(InvalidInputException e , HttpServletRequest request){
        var err = init(e,"InvalidInput",HttpStatus.BAD_REQUEST,request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(InsufficientProductException.class)
    public ResponseEntity<StandardError> orderNotFound(InsufficientProductException e , HttpServletRequest request){
        var err = init(e,"InsufficientProducts",HttpStatus.BAD_REQUEST,request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }



}
