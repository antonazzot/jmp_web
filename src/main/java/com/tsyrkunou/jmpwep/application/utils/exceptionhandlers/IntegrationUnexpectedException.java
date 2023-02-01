package com.tsyrkunou.jmpwep.application.utils.exceptionhandlers;

import java.io.Serializable;

public class IntegrationUnexpectedException
        extends SingleValidationException {
    public IntegrationUnexpectedException(String code, Serializable... args) {
        super(code, args);
    }

    public IntegrationUnexpectedException(String code, Throwable e, Serializable... args) {
        super(code, e, args);
    }
}
