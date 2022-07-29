package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

public class MyAppException extends RuntimeException {
    public MyAppException() {
        super();
    }

    public MyAppException(String message) {
        super(message);
    }

    public MyAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
