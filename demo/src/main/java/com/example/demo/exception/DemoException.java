package com.example.demo.exception;

public abstract class DemoException extends RuntimeException {
    private String code;

    public DemoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DemoException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DemoException(String message) {
        super(message);
        this.code = "";
    }

    public DemoException() {
        super("");
        this.code = "";
    }

    public String getCode() {
        return this.code;
    }
}
