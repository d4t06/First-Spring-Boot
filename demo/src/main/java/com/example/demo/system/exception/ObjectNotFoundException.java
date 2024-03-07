package com.example.demo.system.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String msg) {
        super(msg == null ? "Object not found" : msg);
    }
}
