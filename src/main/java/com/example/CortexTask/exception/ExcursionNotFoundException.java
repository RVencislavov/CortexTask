package com.example.CortexTask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcursionNotFoundException extends RuntimeException {
    public ExcursionNotFoundException(String message) {
        super(message);
    }
}
