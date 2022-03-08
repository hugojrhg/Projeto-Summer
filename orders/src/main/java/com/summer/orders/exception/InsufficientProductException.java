package com.summer.orders.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus()
public class InsufficientProductException extends Exception{
    public InsufficientProductException(String message) {
        super(message);
    }
}
