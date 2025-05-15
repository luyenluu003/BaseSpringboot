package com.example.demo.exception;

public class NonHandleException extends DemoException{
    public NonHandleException(String message) {
        super(message);
    }

    public NonHandleException(){
        super();
    }
}
